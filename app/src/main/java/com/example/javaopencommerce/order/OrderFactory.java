package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.Vat;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.OrderModel.SimpleProduct;
import com.example.javaopencommerce.order.dtos.OrderDto;
import java.time.LocalDate;
import java.util.List;

class OrderFactory {

  OrderModel toOrderModel(OrderDto orderDto) {
    List<SimpleProduct> orderBody = getOrderBody(orderDto.getCard().getProducts());

    return OrderModel.builder()
        .id(orderDto.getId())
        .paymentMethod(PaymentMethod.valueOf(orderDto.getPaymentMethod()))
        .orderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()))
        .paymentStatus(PaymentStatus.valueOf(orderDto.getPaymentStatus()))
        .creationDate(LocalDate.now())
        .orderValueNett(Value.of(orderDto.getCard().getCardValueNett()))
        .orderValueGross(Value.of(orderDto.getCard().getCardValueGross()))
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
