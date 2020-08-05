package com.example.database.services;

import com.example.business.CardModel;
import com.example.business.models.AddressModel;
import com.example.business.models.ItemModel;
import com.example.business.models.ProductModel;
import com.example.database.entity.Address;
import com.example.database.entity.Product;
import com.example.database.repositories.implementations.CardRepositoryImpl;
import com.example.utils.converters.AddressConverter;
import com.example.utils.converters.CardConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class CardService {

    private final ItemService itemService;
    private final CardRepositoryImpl cardRepository;

    public CardService(ItemService itemService, CardRepositoryImpl cardRepository) {
        this.itemService = itemService;
        this.cardRepository = cardRepository;
    }

    public Uni<CardModel> getCard(String id) {
        return cardRepository.getCardList(id).onItem().produceUni(products ->
            getCardProducts(products).onItem().apply(CardModel::new));
    }

    public Uni<CardModel> addProductToCard(Product product, String id) {
        return Uni.combine().all().unis(getCard(id), itemService.getItemById(product.getItemId()))
                .combinedWith((cardModel, itemModel) -> {
                    cardModel.addProduct(itemModel, product.getAmount());
                    return cardModel;
        }).onItem().produceUni(card -> {
                    cardRepository.saveCard(id, CardConverter.convertToProductList(card));
                    return Uni.createFrom().item(card);
                });
    }

    public void flushCard(String id) {
        cardRepository.saveCard(id, emptyList());
    }

    public AddressModel getAddressModel(Long id) {
        Address address = ofNullable(new Address()) //TODO
                .orElseThrow(() ->
                        new WebApplicationException("Address with id " + id + " not found", Response.Status.NOT_FOUND));

        return AddressConverter
                .convertToModel(address);
    }

    private Uni<Map<Long, ProductModel>> getCardProducts(List<Product> products) {
        List<Long> ids = products.stream().map(id -> id.getItemId()).collect(toList());

        return itemService.getItemsListByIdList(ids).onItem().apply(itemModels -> {
            Map<Long, ProductModel> cardProducts = new HashMap<>();
            for (ItemModel im : itemModels) {

                int amount = products.stream()
                        .filter(p -> p.getItemId() == im.getId())
                        .findFirst()
                        .orElse(Product.builder().amount(1).build())
                        .getAmount();

                cardProducts.put(im.getId(), ProductModel.getProduct(im, amount));
            }
            return cardProducts;
        });
    }

    public List<ItemModel> getShippingMethods() {
        return null; //TODO
//        return itemRepository.getShippingMethodList().stream()
//                .map(i -> ItemConverter.convertToModel(i))
//                .collect(Collectors.toList());

    }
}
