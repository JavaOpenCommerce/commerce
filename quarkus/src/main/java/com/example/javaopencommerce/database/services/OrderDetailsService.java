package com.example.javaopencommerce.database.services;

import static com.example.javaopencommerce.utils.converters.JsonConverter.convertToObject;
import static io.smallrye.mutiny.Uni.combine;
import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.address.AddressRepository;
import com.example.javaopencommerce.item.Item;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.order.OrderDetailsEntity;
import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsRepository;
import com.example.javaopencommerce.order.Product;
import com.example.javaopencommerce.utils.converters.AddressConverter;
import com.example.javaopencommerce.utils.converters.OrderDetailsConverter;
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


    public Uni<OrderDetails> getOrderDetailsById(Long id) {
        log.info("Fetching OrderDetails with id: " + id);

        Uni<OrderDetailsEntity> orderDetailsUni = this.orderDetailsRepository.findOrderDetailsById(id);

        Uni<Map<Long, Product>> itemQuantityListUni = orderDetailsUni
                .flatMap(od -> getProducts(od.getProductsJson()));

        Uni<AddressEntity> addressUni = orderDetailsUni.flatMap(od -> this.addressRepository.findById(od.getId()));

        return combine().all().unis(orderDetailsUni, itemQuantityListUni, addressUni)
                .combinedWith(OrderDetailsConverter::convertToModel);
    }

    @Transactional
    public Uni<OrderDetails> saveOrderDetails(Uni<OrderDetails> orderDetailsModel) {

        return orderDetailsModel.flatMap(od -> {
            updateItemStocks(od.getCard().getProducts());

            Uni<OrderDetailsEntity> savedOrderDetails = this.orderDetailsRepository
                    .saveOrder(OrderDetailsConverter.convertToEntity(od));

            return savedOrderDetails
                    .flatMap(sod -> {
                        log.info("Order details persisted with id: {}", sod.getId());
                        Uni<Map<Long, Product>> products = getProducts(sod.getProductsJson());
                        return products.map(prods ->
                                OrderDetailsConverter.convertToModel(
                                        sod,
                                        prods,
                                        AddressConverter.convertToEntity(od.getAddress())
                        ));
                    });
        });
    }

    private void updateItemStocks(Map<Long, Product> productsMap) {

        productsMap.forEach((id, product) ->
                this.itemService.changeStock(id, product.getAmount().asInteger()).await().indefinitely()
        );
    }

    private Uni<Map<Long, Product>> getProducts(String productsJson) {
        List<CardProductEntity> cardProducts =
                convertToObject(productsJson, new ArrayList<CardProductEntity>() {
                }
                        .getClass().getGenericSuperclass());

        List<Long> ids = getProductIdList(cardProducts);

        return this.itemService.getItemsListByIdList(ids).map(itemModels -> {
            Map<Long, Product> cardProductsMap = new HashMap<>();
            for (Item im : itemModels) {
                int amount = cardProducts.stream()
                        .filter(Objects::nonNull)
                        .filter(p -> p.getItemId().equals(im.getId()))
                        .findFirst()
                        .orElse(CardProductEntity.builder().amount(1).build())
                        .getAmount();
                cardProductsMap.put(im.getId(), Product.getProduct(im, amount));
            }
            return cardProductsMap;
        });
    }

    private List<Long> getProductIdList(List<CardProductEntity> cardProducts) {
        return cardProducts.stream()
                .filter(Objects::nonNull)
                .map(CardProductEntity::getItemId)
                .collect(toList());
    }
}
