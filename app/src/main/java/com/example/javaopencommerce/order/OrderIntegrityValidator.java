package com.example.javaopencommerce.order;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.example.javaopencommerce.item.ItemQueryRepository;
import com.example.javaopencommerce.item.dtos.CardDto;
import com.example.javaopencommerce.item.dtos.ItemDto;
import com.example.javaopencommerce.item.dtos.ProductDto;
import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import com.example.javaopencommerce.order.exceptions.AddressNotProvidedValidationException;
import com.example.javaopencommerce.order.exceptions.OrderStateValidationException;
import com.example.javaopencommerce.order.exceptions.OrderValidationException;
import com.example.javaopencommerce.order.exceptions.OrderValueNotMatchingValidationException;
import com.example.javaopencommerce.order.exceptions.OutOfStockItemsValidationException;
import io.smallrye.mutiny.Uni;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


class OrderIntegrityValidator {

  private final ItemQueryRepository itemQueryRepository;

  OrderIntegrityValidator(ItemQueryRepository itemQueryRepository) {
    this.itemQueryRepository = itemQueryRepository;
  }

  public Uni<Void> validateOrder(OrderDetailsDto order) {
    List<OrderValidationException> orderInaccuracies = new ArrayList<>();

    orderInaccuracies.addAll(validateOrderBasics(order));
    orderInaccuracies.addAll(validateOrderState(order));

    fetchCurrentItems(order.getCard().getProducts())
        .onItem().invoke(
        items -> orderInaccuracies.addAll(
            validateOrderValue(items, order.getCard())))
        .onItem().invoke(
        items -> orderInaccuracies.addAll(
            validateStocks(items, order.getCard().getProducts())))
        .await().indefinitely();

    if (!orderInaccuracies.isEmpty()) {
      throw new OrderValidationException(orderInaccuracies);
    }

    return Uni.createFrom().voidItem();
  }

  private Uni<List<ItemDto>> fetchCurrentItems(List<ProductDto> products) {
    List<Long> itemIds = products.stream()
        .filter(Objects::nonNull)
        .map(ProductDto::getItem)
        .filter(Objects::nonNull)
        .map(ItemDto::getId)
        .collect(toUnmodifiableList());
    return itemQueryRepository.getItemsByIdList(itemIds);
  }

  private List<OrderValidationException> validateOrderBasics(OrderDetailsDto order) {
    if (order.getAddressId() == null) {
      return List.of(new AddressNotProvidedValidationException());
    }
    return emptyList();
  }

  private List<OrderValidationException> validateOrderState(OrderDetailsDto order) {
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
        .collect(Collectors.toList());

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
