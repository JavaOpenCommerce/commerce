package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.order.OrderModel.SimpleProduct;
import com.example.javaopencommerce.statics.JsonConverter;
import java.util.List;

class OrderRepositoryImpl implements OrderRepository {

  private final PsqlOrderRepository psqlOrderRepository;

  OrderRepositoryImpl(PsqlOrderRepository psqlOrderRepository) {
    this.psqlOrderRepository = psqlOrderRepository;
  }

  @Override
  public List<OrderModel> findOrderByUserId(Long id) {
    return psqlOrderRepository.findOrderByUserId(id)
        .map(od -> od.stream().map(OrderEntity::toOrderModel).toList()).await().indefinitely();
  }

  @Override
  public OrderModel findOrderById(Long id) {
    return psqlOrderRepository.findOrderById(id).map(OrderEntity::toOrderModel).await()
        .indefinitely();
  }

  @Override
  public OrderModel saveOrder(OrderModel orderModel) {
    List<SimpleProductEntity> products = orderModel.getSnapshot().getOrderBody().stream()
        .map(this::toSimpleProductEntity).toList();
    return psqlOrderRepository.saveOrder(toOrderEntity(orderModel), products)
        .map(OrderEntity::toOrderModel).await().indefinitely();
  }

  private OrderEntity toOrderEntity(OrderModel orderModel) {
    OrderSnapshot orderSnapshot = orderModel.getSnapshot();
    return OrderEntity.builder().id(orderSnapshot.getId())
        .creationDate(orderSnapshot.getCreationDate())
        .orderStatus(orderSnapshot.getOrderStatus().name())
        .paymentMethod(orderSnapshot.getPaymentMethod().name())
        .paymentStatus(orderSnapshot.getPaymentStatus().name())
        .valueGross(orderSnapshot.getOrderValueGross().asDecimal())
        .valueNett(orderSnapshot.getOrderValueNett().asDecimal()).simpleProductsJson(
            JsonConverter.convertToJson(
                orderSnapshot.getOrderBody().stream().map(this::toSimpleProductEntity)
                    .collect(toList()))).build();
  }

  private SimpleProductEntity toSimpleProductEntity(SimpleProduct simpleProduct) {
    return SimpleProductEntity.builder().itemId(simpleProduct.getItemId())
        .amount(simpleProduct.getAmount().asInteger()).name(simpleProduct.getName())
        .valueGross(simpleProduct.getValueGross().asDecimal())
        .vat(simpleProduct.getVat().asDouble()).build();
  }
}
