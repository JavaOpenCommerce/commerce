package com.example.javaopencommerce.order;


import com.example.javaopencommerce.item.ItemQueryRepository;
import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.OrderModel.SimpleProduct;
import com.example.javaopencommerce.order.dtos.OrderDto;
import java.util.List;

class OrderDtoFactory {

  private final ItemQueryRepository itemQueryRepository;

  OrderDtoFactory(ItemQueryRepository itemQueryRepository) {
    this.itemQueryRepository = itemQueryRepository;
  }

  OrderDto toDto(OrderModel order) {
    OrderSnapshot orderSnapshot = order.getSnapshot();
    List<SimpleProduct> products = orderSnapshot.getOrderBody();

    List<Long> itemIds = products.stream()
        .map(SimpleProduct::getItemId)
        .toList();

    List<ProductDto> productDtos = itemQueryRepository.getItemsByIdList(itemIds).stream()
        .map(itemDto ->
            itemToProductDto(
                itemDto,
                products.stream()
                    .filter(p -> p.getItemId().equals(itemDto.getId()))
                    .findAny()
                    .orElseThrow()
                    .getAmount()
                    .asInteger()))
        .toList();

    return getOrderDto(orderSnapshot, productDtos);
  }

  private OrderDto getOrderDto(OrderSnapshot orderSnapshot, List<ProductDto> productDtos) {
    CardDto card = CardDto.builder()
        .cardValueGross(orderSnapshot.getOrderValueGross().asDecimal())
        .cardValueNett(orderSnapshot.getOrderValueNett().asDecimal())
        .products(productDtos)
        .build();

    return OrderDto.builder()
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
