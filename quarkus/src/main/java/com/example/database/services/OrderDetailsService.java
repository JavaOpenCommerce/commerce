package com.example.database.services;

import static com.example.utils.converters.JsonConverter.convertToObject;
import static io.smallrye.mutiny.Uni.combine;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.address.AddressRepository;
import com.example.javaopencommerce.item.ItemModel;
import com.example.javaopencommerce.order.CardProduct;
import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsModel;
import com.example.javaopencommerce.order.OrderDetailsRepository;
import com.example.javaopencommerce.order.ProductModel;
import com.example.utils.converters.AddressConverter;
import com.example.utils.converters.OrderDetailsConverter;
import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final AddressRepository addressRepository;
    private final ItemService itemService;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository,
                               AddressRepository addressRepository,
                               ItemService itemService) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.addressRepository = addressRepository;
        this.itemService = itemService;
    }


    public Uni<OrderDetailsModel> getOrderDetailsById(Long id) {
        log.info("Fetching OrderDetails with id: " + id);

        Uni<OrderDetails> orderDetailsUni = this.orderDetailsRepository.findOrderDetailsById(id);

        Uni<Map<Long, ProductModel>> itemQuantityListUni = orderDetailsUni
                .flatMap(od -> getProducts(od.getProductsJson()));

        Uni<AddressEntity> addressUni = orderDetailsUni.flatMap(od -> this.addressRepository.findById(od.getId()));

        return combine().all().unis(orderDetailsUni, itemQuantityListUni, addressUni)
                .combinedWith(OrderDetailsConverter::convertToModel);
    }

    @Transactional
    public Uni<OrderDetailsModel> saveOrderDetails(Uni<OrderDetailsModel> orderDetailsModel) {

        return orderDetailsModel.flatMap(od -> {
            updateItemStocks(od.getCard().getProducts());

            Uni<OrderDetails> savedOrderDetails = this.orderDetailsRepository
                    .saveOrder(OrderDetailsConverter.convertToEntity(od));

            return savedOrderDetails
                    .flatMap(sod -> {
                        log.info("Order details persisted with id: {}", sod.getId());
                        Uni<Map<Long, ProductModel>> products = getProducts(sod.getProductsJson());
                        return products.map(prods ->
                                OrderDetailsConverter.convertToModel(
                                        sod,
                                        prods,
                                        AddressConverter.convertToEntity(od.getAddress())
                        ));
                    });
        });
    }

    private void updateItemStocks(Map<Long, ProductModel> productsMap) {

        productsMap.forEach((id, product) ->
                this.itemService.changeStock(id, product.getAmount().asInteger()).await().indefinitely()
        );
    }

    private Uni<Map<Long, ProductModel>> getProducts(String productsJson) {
        List<CardProduct> cardProducts =
                convertToObject(productsJson, new ArrayList<CardProduct>() {
                }
                        .getClass().getGenericSuperclass());

        List<Long> ids = getProductIdList(cardProducts);

        return this.itemService.getItemsListByIdList(ids).map(itemModels -> {
            Map<Long, ProductModel> cardProductsMap = new HashMap<>();
            for (ItemModel im : itemModels) {
                int amount = cardProducts.stream()
                        .filter(Objects::nonNull)
                        .filter(p -> p.getItemId().equals(im.getId()))
                        .findFirst()
                        .orElse(CardProduct.builder().amount(1).build())
                        .getAmount();
                cardProductsMap.put(im.getId(), ProductModel.getProduct(im, amount));
            }
            return cardProductsMap;
        });
    }

    private List<Long> getProductIdList(List<CardProduct> cardProducts) {
        return cardProducts.stream()
                .filter(Objects::nonNull)
                .map(CardProduct::getItemId)
                .collect(toList());
    }
}
