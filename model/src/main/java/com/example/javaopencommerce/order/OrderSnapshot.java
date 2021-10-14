package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.OrderModel.SimpleProduct;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;


@lombok.Value
@Builder
class OrderSnapshot {

  Long id;
  LocalDate creationDate;

  @Builder.Default
  PaymentStatus paymentStatus = PaymentStatus.BEFORE_PAYMENT;

  @Builder.Default
  PaymentMethod paymentMethod = PaymentMethod.MONEY_TRANSFER;

  @Builder.Default
  OrderStatus orderStatus = OrderStatus.NEW;

  Value orderValueGross;
  Value orderValueNett;
  List<SimpleProduct> orderBody;
}
