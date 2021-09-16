package com.example.javaopencommerce.utils.converters;

import static com.example.javaopencommerce.utils.converters.JsonConverter.convertToJson;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.order.Card;
import com.example.javaopencommerce.order.CardProductEntity;
import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsDto;
import com.example.javaopencommerce.order.OrderDetailsEntity;
import com.example.javaopencommerce.order.OrderStatus;
import com.example.javaopencommerce.order.PaymentMethod;
import com.example.javaopencommerce.order.PaymentStatus;
import com.example.javaopencommerce.order.Product;
import java.util.List;
import java.util.Map;

public interface OrderDetailsConverter {

    static OrderDetails convertToModel(OrderDetailsEntity orderDetails, Map<Long, Product> products, AddressEntity address) {

        return OrderDetails.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus().toString())
                .paymentMethod(orderDetails.getPaymentMethod().toString())
                .paymentStatus(orderDetails.getPaymentStatus().toString())
                .address(AddressConverter.convertToModel(address))
                .card(Card.getInstance(products))
                .build();
    }

    static OrderDetails convertDtoToModel(OrderDetailsDto orderDetails) {
        Map<Long, Product> products = orderDetails.getCard().getProducts().values().stream()
                .map(ProductConverter::convertDtoToModel)
                .collect(toMap(pm -> pm.getItemModel().getId(), pm -> pm));

        return OrderDetails.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus())
                .paymentMethod(orderDetails.getPaymentMethod())
                .paymentStatus(orderDetails.getPaymentStatus())
                .address(AddressConverter.convertDtoToModel(orderDetails.getAddress()))
                .card(Card.getInstance(products))
                .build();
    }

    static OrderDetailsDto convertToDto(OrderDetails orderDetails, String lang, String defaultLang) {

        return OrderDetailsDto.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus())
                .paymentMethod(orderDetails.getPaymentMethod())
                .paymentStatus(orderDetails.getPaymentStatus())
                .address(AddressConverter.convertToDto(orderDetails.getAddress()))
                .card(CardConverter.convertToDto(orderDetails.getCard(), lang, defaultLang))
                .build();
    }

    static OrderDetailsEntity convertToEntity(OrderDetails orderDetailsModel) {

        List<CardProductEntity> productList = orderDetailsModel.getCard().getProducts().values()
                .stream()
                .map(ProductConverter::convertModelToCardProduct)
                .collect(toList());

        return OrderDetailsEntity.builder()
                .creationDate(orderDetailsModel.getCreationDate())
                .shippingAddressId(orderDetailsModel.getAddress().getId())
                .orderStatus(OrderStatus.valueOf(orderDetailsModel.getOrderStatus()))
                .paymentMethod(PaymentMethod.valueOf(orderDetailsModel.getPaymentMethod()))
                .paymentStatus(PaymentStatus.valueOf(orderDetailsModel.getPaymentStatus()))
                .productsJson(convertToJson(productList))
                .build();
    }
}
