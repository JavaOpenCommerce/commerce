package com.example.javaopencommerce.order;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import com.example.javaopencommerce.item.ItemQueryRepository;
import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.dtos.OrderDto;
import com.example.javaopencommerce.order.exceptions.validation.AddressNotProvidedValidationException;
import com.example.javaopencommerce.order.exceptions.validation.OrderStateValidationException;
import com.example.javaopencommerce.order.exceptions.validation.OrderValidationException;
import com.example.javaopencommerce.order.exceptions.validation.OrderValueNotMatchingValidationException;
import com.example.javaopencommerce.order.exceptions.validation.OutOfStockItemsValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;

@Log4j2
class OrderIntegrityValidator {

  private final ItemQueryRepository itemQueryRepository;

  OrderIntegrityValidator(ItemQueryRepository itemQueryRepository) {
    this.itemQueryRepository = itemQueryRepository;
  }

  public void validateOrder(OrderDto order) {
    List<OrderValidationException> orderInaccuracies = new ArrayList<>();

    orderInaccuracies.addAll(validateOrderBasics(order));
    orderInaccuracies.addAll(validateOrderState(order));

    List<ItemDto> items = fetchCurrentItems(order.getCard().getProducts());
    orderInaccuracies.addAll(validateOrderValue(items, order.getCard()));
    orderInaccuracies.addAll(validateStocks(items, order.getCard().getProducts()));

    if (!orderInaccuracies.isEmpty()) {
      log.warn("Order validation failed with causes: {}", orderInaccuracies);
      throw new OrderValidationException(orderInaccuracies);
    }
  }

  private List<ItemDto> fetchCurrentItems(List<ProductDto> products) {
    List<Long> itemIds = products.stream()
        .filter(Objects::nonNull)
        .map(ProductDto::getItem)
        .filter(Objects::nonNull)
        .map(ItemDto::getId)
        .toList();
    return itemQueryRepository.getItemsByIdList(itemIds);
  }

  //TODO validate if address witch such id exists!!
  private List<OrderValidationException> validateOrderBasics(OrderDto order) {
    return Optional.ofNullable(order.getAddressId())
        .map(a -> Collections.<OrderValidationException>emptyList())
        .orElse(List.of(new AddressNotProvidedValidationException()));
  }


  private List<OrderValidationException> validateOrderState(OrderDto order) {
    List<OrderValidationException> inaccuracies = new ArrayList<>();
    if (!OrderStatus.NEW.toString().equals(order.getOrderStatus())) {
      inaccuracies.add(new OrderStateValidationException(
          format("Wrong/Illegal order status: %s", order.getOrderStatus())));
    }

    if (!PaymentStatus.BEFORE_PAYMENT.toString().equals(order.getPaymentStatus())) {
      inaccuracies.add(new OrderStateValidationException(
          format("Wrong/Illegal payment status: %s", order.getPaymentStatus())));
    }

    try {
      PaymentMethod.valueOf(order.getPaymentMethod());
    } catch (IllegalArgumentException e) {
      inaccuracies.add(new OrderStateValidationException(
          format("Wrong/Illegal payment method: %s", order.getPaymentMethod())));
    }
    return inaccuracies;
  }

  private List<OrderValidationException> validateOrderValue(List<ItemDto> currentItems,
      CardDto card) {
    Map<Long, BigDecimal> currentItemPrices = currentItems.stream()
        .filter(Objects::nonNull)
        .collect(toMap(ItemDto::getId, ItemDto::getValueGross));

    BigDecimal currentOrderValue = calculateCurrentOrderValue(card.getProducts(),
        currentItemPrices);

    if (!currentOrderValue.equals(card.getCardValueGross())) {
      return List.of(
          new OrderValueNotMatchingValidationException(card.getCardValueGross(),
              currentOrderValue));
    }
    return emptyList();
  }

  private BigDecimal calculateCurrentOrderValue(List<ProductDto> orderedProducts,
      Map<Long, BigDecimal> currentPrices) {

    return orderedProducts.stream()
        .map(product ->
            ofNullable(currentPrices.get(product.getItem().getId()))
                .orElseThrow(
                    () -> new OrderValidationException(
                        format("Price for item with id: %s, not found!",
                            product.getItem().getId())))
                .multiply(BigDecimal.valueOf(product.getAmount())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<OutOfStockItemsValidationException> validateStocks(List<ItemDto> currentItems,
      List<ProductDto> orderedProducts) {
    Map<Long, Integer> currentStocks = currentItems.stream()
        .collect(toMap(ItemDto::getId, ItemDto::getStock));

    List<ProductDto> outOfStockItems = orderedProducts.stream()
        .filter(product -> !isEnoughInStock(currentStocks, product))
        .toList();

    if (!outOfStockItems.isEmpty()) {
      return List.of(new OutOfStockItemsValidationException(outOfStockItems));
    }
    return emptyList();
  }

  private boolean isEnoughInStock(Map<Long, Integer> currentStocks, ProductDto orderedProduct) {
    int currentStock = ofNullable(
        currentStocks.get(orderedProduct.getItem().getId()))
        .orElseThrow(
            () -> new OrderValidationException(
                format("Price for item with id: %s, not found!",
                    orderedProduct.getItem().getId())));
    return orderedProduct.getAmount() <= currentStock;
  }
}
