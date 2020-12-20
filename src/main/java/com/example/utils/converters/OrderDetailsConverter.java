package com.example.utils.converters;

import com.example.business.models.CardModel;
import com.example.business.models.OrderDetailsModel;
import com.example.business.models.ProductModel;
import com.example.business.models.UserModel;
import com.example.database.entity.*;
import dtos.OrderDetailsDto;

import java.util.List;
import java.util.Map;

import static com.example.utils.converters.JsonConverter.convertToJson;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public interface OrderDetailsConverter {

    static OrderDetailsModel convertToModel(OrderDetails orderDetails, Map<Long, ProductModel> products, Address address, UserEntity userEntity) {

        return OrderDetailsModel.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus().toString())
                .paymentMethod(orderDetails.getPaymentMethod().toString())
                .paymentStatus(orderDetails.getPaymentStatus().toString())
                .address(AddressConverter.convertToModel(address))
                .user(UserModel.builder().id(1L).build()) //TODO
                .card(CardModel.getInstance(products))
                .build();
    }

    static OrderDetailsModel convertDtoToModel(OrderDetailsDto orderDetails) {
        Map<Long, ProductModel> products = orderDetails.getCard().getProducts().values().stream()
                .map(ProductConverter::convertDtoToModel)
                .collect(toMap(pm -> pm.getItemModel().getId(), pm -> pm));

        return OrderDetailsModel.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus())
                .paymentMethod(orderDetails.getPaymentMethod())
                .paymentStatus(orderDetails.getPaymentStatus())
                .address(AddressConverter.convertDtoToModel(orderDetails.getAddress()))
                .user(UserModel.builder().id(1L).build()) //TODO
                .card(CardModel.getInstance(products))
                .build();
    }

    static OrderDetailsDto convertToDto(OrderDetailsModel orderDetails, String lang, String defaultLang) {

        return OrderDetailsDto.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus())
                .paymentMethod(orderDetails.getPaymentMethod())
                .paymentStatus(orderDetails.getPaymentStatus())
                .address(AddressConverter.convertToDto(orderDetails.getAddress()))
                //.user()
                .card(CardConverter.convertToDto(orderDetails.getCard(), lang, defaultLang))
                .build();
    }

    static OrderDetails convertToEntity(OrderDetailsModel orderDetailsModel) {

        List<CardProduct> productList = orderDetailsModel.getCard().getProducts().values()
                .stream()
                .map(ProductConverter::convertModelToCardProduct)
                .collect(toList());

        return OrderDetails.builder()
                .creationDate(orderDetailsModel.getCreationDate())
                .shippingAddressId(orderDetailsModel.getAddress().getId())
                .orderStatus(OrderStatus.valueOf(orderDetailsModel.getOrderStatus()))
                .paymentMethod(PaymentMethod.valueOf(orderDetailsModel.getPaymentMethod()))
                .paymentStatus(PaymentStatus.valueOf(orderDetailsModel.getPaymentStatus()))
                .userEntityId(orderDetailsModel.getUser().getId())
                .productsJson(convertToJson(productList))
                .build();
    }
}
