package com.example.database.services;

import com.example.business.models.ItemModel;
import com.example.business.models.OrderDetailsModel;
import com.example.business.models.ProductModel;
import com.example.database.entity.Address;
import com.example.database.entity.CardProduct;
import com.example.database.entity.OrderDetails;
import com.example.database.entity.UserEntity;
import com.example.database.repositories.interfaces.AddressRepository;
import com.example.database.repositories.interfaces.OrderDetailsRepository;
import com.example.database.repositories.interfaces.UserRepository;
import com.example.utils.converters.AddressConverter;
import com.example.utils.converters.OrderDetailsConverter;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.*;

import static com.example.utils.converters.JsonConverter.convertToObject;
import static io.smallrye.mutiny.Uni.combine;
import static java.util.stream.Collectors.toList;

@Slf4j
@ApplicationScoped
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository,
                               AddressRepository addressRepository,
                               UserRepository userRepository,
                               ItemService itemService) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
    }


    public Uni<OrderDetailsModel> getOrderDetailsById(Long id) {
        log.info("Fetching OrderDetails with id: " + id);

        Uni<OrderDetails> orderDetailsUni = this.orderDetailsRepository.findOrderDetailsById(id);

        Uni<Map<Long, ProductModel>> itemQuantityListUni = orderDetailsUni
                .flatMap(od -> getProducts(od.getProductsJson()));

        Uni<Address> addressUni = orderDetailsUni.flatMap(od -> this.addressRepository.findById(od.getId()));

        Uni<UserEntity> userUni = orderDetailsUni.flatMap(od -> this.userRepository.findById(od.getId()));

        return combine().all().unis(orderDetailsUni, itemQuantityListUni, addressUni, userUni)
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
                                        AddressConverter.convertToEntity(od.getAddress()),
                                        UserEntity.builder().id(1L).build()) //TODO
                        );
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
