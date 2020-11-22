package com.example.utils.converters;

import com.example.business.models.CardModel;
import com.example.business.models.OrderDetailsModel;
import com.example.business.models.ProductModel;
import com.example.database.entity.*;
import com.example.rest.dtos.OrderDetailsDto;

import java.util.Map;

import static com.example.utils.converters.JsonConverter.convertToJson;

public interface OrderDetailsConverter {

    static OrderDetailsModel convertToModel(OrderDetails orderDetails, Map<Long, ProductModel> products, Address address, UserEntity userEntity) {

        return OrderDetailsModel.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus().toString())
                .paymentMethod(orderDetails.getPaymentMethod().toString())
                .paymentStatus(orderDetails.getPaymentStatus().toString())
                .address(AddressConverter.convertToModel(address))
                //.user(UserConverter.convertToModel(userEntity))
                .card(CardModel.getInstance(products))
                .build();
    }

    static OrderDetailsModel convertDtoToModel(OrderDetailsDto orderDetails) {
        return OrderDetailsModel.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus())
                .paymentMethod(orderDetails.getPaymentMethod())
                .paymentStatus(orderDetails.getPaymentStatus())
                //.address(AddressConverter.convertToModel(address))
                //.user(UserConverter.convertToModel(userEntity))
                //.card(CardModel.getInstance(products))
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

    //Requires OrderDetailsModel
    static OrderDetails convertToEntity(OrderDetailsModel orderDetailsModel) {

        return OrderDetails.builder()
                .creationDate(orderDetailsModel.getCreationDate())
                .shippingAddressId(orderDetailsModel.getAddress().getId())
                .orderStatus(OrderStatus.valueOf(orderDetailsModel.getOrderStatus()))
                .paymentMethod(PaymentMethod.valueOf(orderDetailsModel.getPaymentMethod()))
                .paymentStatus(PaymentStatus.valueOf(orderDetailsModel.getPaymentMethod()))
                .userEntityId(orderDetailsModel.getUser().getId())
                .productsJson(convertToJson(orderDetailsModel.getCard().getProducts().values()))
                .build();
    }
}
