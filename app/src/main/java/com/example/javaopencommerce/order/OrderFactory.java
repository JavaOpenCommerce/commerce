package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.OrderDetails.SimpleProduct;
import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import java.time.LocalDate;
import java.util.List;

class OrderFactory {

  OrderDetails toOrderModel(OrderDetailsDto orderDetailsDto) {
    List<SimpleProduct> orderBody = getOrderBody(orderDetailsDto.getCard().getProducts());

    return OrderDetails.builder()
        .id(orderDetailsDto.getId())
        .paymentMethod(PaymentMethod.valueOf(orderDetailsDto.getPaymentMethod()))
        .orderStatus(OrderStatus.valueOf(orderDetailsDto.getOrderStatus()))
        .paymentStatus(PaymentStatus.valueOf(orderDetailsDto.getPaymentStatus()))
        .creationDate(LocalDate.now())
        .orderValueNett(Value.of(orderDetailsDto.getCard().getCardValueNett()))
        .orderValueGross(Value.of(orderDetailsDto.getCard().getCardValueGross()))
        .orderBody(orderBody)
        .build();
  }

  private List<SimpleProduct> getOrderBody(List<ProductDto> products) {
    return products.stream()
        .map(this::toSimpleProduct)
        .collect(toUnmodifiableList());
  }

  private SimpleProduct toSimpleProduct(ProductDto product) {
    return SimpleProduct.builder()
        .itemId(product.getItem().getId())
        .amount(Amount.of(product.getAmount()))
        .name(product.getItem().getName())
        .valueGross(Value.of(product.getItem().getValueGross()))
        .vat(Vat.of(product.getItem().getVat()))
        .build();
  }
}
