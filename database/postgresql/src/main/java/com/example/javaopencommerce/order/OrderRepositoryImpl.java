package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;

import com.example.javaopencommerce.order.OrderDetails.SimpleProduct;
import com.example.javaopencommerce.statics.JsonConverter;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

class OrderRepositoryImpl implements OrderRepository {

  private final PsqlOrderRepository psqlOrderRepository;

  OrderRepositoryImpl(PsqlOrderRepository psqlOrderRepository) {
    this.psqlOrderRepository = psqlOrderRepository;
  }

  @Override
  public Uni<List<OrderDetails>> findOrderDetailsByUserId(Long id) {
    return psqlOrderRepository.findOrderDetailsByUserId(id)
        .map(od ->
            od.stream()
                .map(OrderDetailsEntity::toOrderDetailsModel)
                .collect(Collectors.toUnmodifiableList()));
  }

  @Override
  public Uni<OrderDetails> findOrderDetailsById(Long id) {
    return psqlOrderRepository.findOrderDetailsById(id)
        .map(OrderDetailsEntity::toOrderDetailsModel);
  }

  @Override
  public Uni<OrderDetails> saveOrder(OrderDetails orderDetails) {
    return psqlOrderRepository.saveOrder(toOrderEntity(orderDetails))
        .map(OrderDetailsEntity::toOrderDetailsModel);
  }

  private OrderDetailsEntity toOrderEntity(OrderDetails orderDetails) {
    OrderSnapshot orderSnapshot = orderDetails.getSnapshot();
    return OrderDetailsEntity.builder()
        .id(orderSnapshot.getId())
        .creationDate(orderSnapshot.getCreationDate())
        .orderStatus(orderSnapshot.getOrderStatus().name())
        .paymentMethod(orderSnapshot.getPaymentMethod().name())
        .paymentStatus(orderSnapshot.getPaymentStatus().name())
        .valueGross(orderSnapshot.getOrderValueGross().asDecimal())
        .valueNett(orderSnapshot.getOrderValueNett().asDecimal())
        .simpleProductsJson(
            JsonConverter.convertToJson(
                orderSnapshot.getOrderBody().stream()
                    .map(this::toSimpleProductEntity)
                    .collect(toList())))
        .build();
  }

  private SimpleProductEntity toSimpleProductEntity(SimpleProduct simpleProduct) {
    return SimpleProductEntity.builder()
        .itemId(simpleProduct.getItemId())
        .amount(simpleProduct.getAmount().asInteger())
        .name(simpleProduct.getName())
        .valueGross(simpleProduct.getValueGross().asDecimal())
        .vat(simpleProduct.getVat().asDouble())
        .build();
  }
}
