package com.example.database.services;

import static java.lang.Long.parseLong;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import com.example.database.repositories.impl.CardRepositoryImpl;
import com.example.elasticsearch.SearchRequest;
import com.example.elasticsearch.SearchService;
import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.address.AddressModel;
import com.example.javaopencommerce.item.ItemModel;
import com.example.javaopencommerce.order.CardModel;
import com.example.javaopencommerce.order.CardProduct;
import com.example.javaopencommerce.order.ProductModel;
import com.example.utils.converters.AddressConverter;
import com.example.utils.converters.CardConverter;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class CardService {

    private final ItemService itemService;
    private final CardRepositoryImpl cardRepository;
    private final SearchService searchService;

    public CardService(ItemService itemService, CardRepositoryImpl cardRepository, SearchService searchService) {
        this.itemService = itemService;
        this.cardRepository = cardRepository;
        this.searchService = searchService;
    }

    public Uni<CardModel> getCard(String id) {
        return this.cardRepository.getCardList(id).flatMap(products ->
                getCardProducts(products).map(CardModel::getInstance));
    }

    public Uni<String> addProductWithAmount(CardProduct product, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemService.getItemById(product.getItemId()))
                .combinedWith((cardModel, itemModel) -> {
                    String result = cardModel.addProduct(itemModel, product.getAmount());
                    this.cardRepository.saveCard(id, CardConverter.convertToProductList(cardModel)).subscribe();
                    return result;
                });
    }

    public Uni<String> increaseProductAmount(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemService.getItemById(itemId))
                .combinedWith((cardModel, itemModel) -> {
                    String result = cardModel.increaseProductAmount(itemModel);
                    this.cardRepository.saveCard(id, CardConverter.convertToProductList(cardModel));
                    return result;
                });
    }

    public Uni<String> decreaseProductAmount(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemService.getItemById(itemId))
                .combinedWith((cardModel, itemModel) -> {
                    String result = cardModel.decreaseProductAmount(itemModel);
                    this.cardRepository.saveCard(id, CardConverter.convertToProductList(cardModel));
                    return result;
                });
    }

    public Uni<String> removeProduct(Long itemId, String id) {
        return Uni.combine().all().unis(getCard(id), this.itemService.getItemById(itemId))
                .combinedWith((cardModel, itemModel) -> {
                    String result = cardModel.removeProduct(itemModel);
                    this.cardRepository.saveCard(id, CardConverter.convertToProductList(cardModel));
                    return result;
                });
    }

    public void flushCard(String id) {
        this.cardRepository.saveCard(id, emptyList());
    }

    public Uni<List<ItemModel>> getShippingMethods() {
        SearchRequest searchRequest = SearchRequest.builder()
                .categoryId(1L)
                .pageNum(0)
                .pageSize(20)
                .build();
        return getFilteredResults(searchRequest)
                .flatMap(this.itemService::getItemsListByIdList);
    }

    public AddressModel getAddressModel(Long id) {
        AddressEntity address = ofNullable(new AddressEntity()) //TODO
                .orElseThrow(() ->
                        new WebApplicationException("Address with id " + id + " not found", Response.Status.NOT_FOUND));
        return AddressConverter
                .convertToModel(address);
    }

    private Uni<List<Long>> getFilteredResults(SearchRequest request) {
        return this.searchService.searchItemsBySearchRequest(request).map(json ->
                ofNullable(json)
                        .map(j -> j.getJsonObject("hits"))
                        .map(hits -> hits.getJsonArray("hits"))
                        .orElse(new JsonArray())
                        .stream()
                        .filter(o -> o instanceof JsonObject)
                        .map(JsonObject.class::cast)
                        .map(o -> parseLong(o.getString("_id")))
                        .collect(toList())
        );
    }

    private Uni<Map<Long, ProductModel>> getCardProducts(List<CardProduct> products) {
        List<Long> ids = products.stream().map(CardProduct::getItemId).collect(toList());

        return this.itemService.getItemsListByIdList(ids).map(itemModels -> {
            Map<Long, ProductModel> cardProducts = new HashMap<>();
            for (ItemModel im : itemModels) {

                int amount = products.stream()
                        .filter(p -> p.getItemId().equals(im.getId()))
                        .findFirst()
                        .orElse(CardProduct.builder().amount(1).build())
                        .getAmount();

                cardProducts.put(im.getId(), ProductModel.getProduct(im, amount));
            }
            return cardProducts;
        });
    }
}
