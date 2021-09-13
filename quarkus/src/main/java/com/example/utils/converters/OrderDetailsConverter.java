package com.example.utils.converters;

import static com.example.utils.converters.JsonConverter.convertToJson;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.example.javaopencommerce.address.AddressEntity;
import com.example.javaopencommerce.order.CardModel;
import com.example.javaopencommerce.order.CardProduct;
import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsDto;
import com.example.javaopencommerce.order.OrderDetailsModel;
import com.example.javaopencommerce.order.OrderStatus;
import com.example.javaopencommerce.order.PaymentMethod;
import com.example.javaopencommerce.order.PaymentStatus;
import com.example.javaopencommerce.order.ProductModel;
import java.util.List;
import java.util.Map;

public interface OrderDetailsConverter {

    static OrderDetailsModel convertToModel(OrderDetails orderDetails, Map<Long, ProductModel> products, AddressEntity address) {

        return OrderDetailsModel.builder()
                .id(orderDetails.getId())
                .creationDate(orderDetails.getCreationDate())
                .orderStatus(orderDetails.getOrderStatus().toString())
                .paymentMethod(orderDetails.getPaymentMethod().toString())
                .paymentStatus(orderDetails.getPaymentStatus().toString())
                .address(AddressConverter.convertToModel(address))
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
                .productsJson(convertToJson(productList))
                .build();
    }
}
