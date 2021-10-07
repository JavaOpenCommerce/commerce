package com.example.javaopencommerce.order;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.item.ItemQueryRepository;
import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.OrderDetails.SimpleProduct;
import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import io.smallrye.mutiny.Uni;
import java.util.List;

class OrderDtoFactory {

  private final ItemQueryRepository itemQueryRepository;

  OrderDtoFactory(ItemQueryRepository itemQueryRepository) {
    this.itemQueryRepository = itemQueryRepository;
  }

  Uni<OrderDetailsDto> toDto(OrderDetails order) {
    OrderSnapshot orderSnapshot = order.getSnapshot();
    List<SimpleProduct> products = orderSnapshot.getOrderBody();

    List<Long> itemIds = products.stream()
        .map(SimpleProduct::getItemId)
        .collect(toUnmodifiableList());

    return itemQueryRepository.getItemsByIdList(itemIds)
        .map(items -> items.stream()
            .map(itemDto ->
                itemToProductDto(
                    itemDto,
                    products.stream()
                        .filter(p -> p.getItemId().equals(itemDto.getId()))
                        .findAny()
                        .orElseThrow()
                        .getAmount()
                        .asInteger()))
            .collect(toList()))
        .map(p -> getOrderDetailsDto(orderSnapshot, p));
  }

  private OrderDetailsDto getOrderDetailsDto(OrderSnapshot orderSnapshot, List<ProductDto> productDtos) {
    CardDto card = CardDto.builder()
        .cardValueGross(orderSnapshot.getOrderValueGross().asDecimal())
        .cardValueNett(orderSnapshot.getOrderValueNett().asDecimal())
        .products(productDtos)
        .build();

    return OrderDetailsDto.builder()
        .id(orderSnapshot.getId())
        .creationDate(orderSnapshot.getCreationDate())
        .orderStatus(orderSnapshot.getOrderStatus().name())
        .paymentMethod(orderSnapshot.getPaymentMethod().name())
        .paymentStatus(orderSnapshot.getPaymentStatus().name())
        .card(card)
        .build();
  }

  private ProductDto itemToProductDto(ItemDto item, int amount) {
    return ProductDto.builder()
        .item(item)
        .amount(amount)
        .build();
  }
}
