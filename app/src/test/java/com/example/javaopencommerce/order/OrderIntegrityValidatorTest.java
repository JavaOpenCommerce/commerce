package com.example.javaopencommerce.order;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.example.javaopencommerce.statics.JsonConverter;
import io.smallrye.mutiny.Uni;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderIntegrityValidatorTest {

  private OrderIntegrityValidator orderIntegrityValidator;
  private final ItemQueryRepository itemQueryRepository = mock(ItemQueryRepository.class);

  @BeforeEach
  void setUp() {
    orderIntegrityValidator = new OrderIntegrityValidator(itemQueryRepository);
    when(itemQueryRepository.getItemsByIdList(any()))
        .thenReturn(Uni.createFrom().item(getItemsList()));
  }

  @Test
  void testValidationPassWithoutExceptions() {
    // given
    CardDto cardDto = CardDto.builder()
        .products(getProductsList(1, 2))
        .cardValueGross(BigDecimal.valueOf(31.09))
        .build();
    OrderDetailsDto order = OrderDetailsDto.builder()
        .orderStatus("NEW")
        .paymentStatus("BEFORE_PAYMENT")
        .paymentMethod("MONEY_TRANSFER")
        .addressId(1L)
        .card(cardDto)
        .build();

    // then
    assertDoesNotThrow(() -> orderIntegrityValidator.validateOrder(order));
  }

  @Test
  void testShouldThrowOrderValueNonMatchingValidationException() {
    // given
    CardDto cardDto = CardDto.builder()
        .products(getProductsList(1, 2))
        .cardValueGross(BigDecimal.valueOf(32.09))
        .build();
    OrderDetailsDto order = OrderDetailsDto.builder()
        .orderStatus("NEW")
        .paymentStatus("BEFORE_PAYMENT")
        .paymentMethod("MONEY_TRANSFER")
        .addressId(1L)
        .card(cardDto)
        .build();

    // when
    OrderValidationException exception = assertThrows(OrderValidationException.class,
        () -> orderIntegrityValidator.validateOrder(order));

    // then
    assertTrue(
        exception.getDerivativeExceptions()
            .get(0) instanceof OrderValueNotMatchingValidationException);

    assertTrue(
        exception.getDerivativeExceptions()
            .get(0).getMessage().contains("Issued order value: 32.09, Calculated value: 31.09"));
  }

  @Test
  void testShouldThrowOutOfStockValidationException() {
    // given
    CardDto cardDto = CardDto.builder()
        .products(getProductsList(6, 2))
        .cardValueGross(BigDecimal.valueOf(86.64))
        .build();
    OrderDetailsDto order = OrderDetailsDto.builder()
        .orderStatus("NEW")
        .paymentStatus("BEFORE_PAYMENT")
        .paymentMethod("MONEY_TRANSFER")
        .addressId(1L)
        .card(cardDto)
        .build();

    // when
    OrderValidationException exception = assertThrows(OrderValidationException.class,
        () -> orderIntegrityValidator.validateOrder(order));

    // then
    assertTrue(
        exception.getDerivativeExceptions()
            .get(0) instanceof OutOfStockItemsValidationException);

    assertTrue(
        exception.getDerivativeExceptions()
            .get(0).getMessage().contains("Not enough items in a stock"));

    assertEquals(format("[%s]", JsonConverter.convertToJson(getProductsList(6, 2).get(0))),
        ((OutOfStockItemsValidationException) exception.getDerivativeExceptions()
            .get(0)).getPayload());
  }

  @Test
  void shouldThrowAddressNotProvidedException() {
    // given
    CardDto cardDto = CardDto.builder()
        .products(getProductsList(1, 2))
        .cardValueGross(BigDecimal.valueOf(31.09))
        .build();
    OrderDetailsDto order = OrderDetailsDto.builder()
        .orderStatus("NEW")
        .paymentStatus("BEFORE_PAYMENT")
        .paymentMethod("MONEY_TRANSFER")
        .card(cardDto)
        .build();

    // when
    OrderValidationException exception = assertThrows(OrderValidationException.class,
        () -> orderIntegrityValidator.validateOrder(order));

    // then
    assertTrue(
        exception.getDerivativeExceptions()
            .get(0) instanceof AddressNotProvidedValidationException);
  }

  @Test
  void shouldThrowOrderStateException() {
    // given
    CardDto cardDto = CardDto.builder()
        .products(getProductsList(1, 2))
        .cardValueGross(BigDecimal.valueOf(31.09))
        .build();
    OrderDetailsDto order = OrderDetailsDto.builder()
        .orderStatus("NEW")
        .paymentStatus("ALREADY_PAID")
        .paymentMethod("MONEY_TRANSFER")
        .card(cardDto)
        .addressId(1L)
        .build();

    // when
    OrderValidationException exception = assertThrows(OrderValidationException.class,
        () -> orderIntegrityValidator.validateOrder(order));

    // then
    assertTrue(
        exception.getDerivativeExceptions()
            .get(0) instanceof OrderStateValidationException);

    assertTrue(
        exception.getDerivativeExceptions()
            .get(0).getMessage().contains("Wrong/Illegal payment status: ALREADY_PAID"));
  }

  private List<ItemDto> getItemsList() {
    ItemDto testItem1 = ItemDto.builder()
        .id(1L)
        .valueGross(BigDecimal.valueOf(11.11))
        .stock(5)
        .build();

    ItemDto testItem2 = ItemDto.builder()
        .id(2L)
        .valueGross(BigDecimal.valueOf(9.99))
        .stock(5)
        .build();

    return List.of(testItem1, testItem2);
  }

  private List<ProductDto> getProductsList(int amount1, int amount2) {
    List<ItemDto> itemsList = getItemsList();
    ProductDto testProduct1 = ProductDto.builder()
        .item(itemsList.get(0))
        .amount(amount1)
        .build();
    ProductDto testProduct2 = ProductDto.builder()
        .item(itemsList.get(1))
        .amount(amount2)
        .build();
    return List.of(testProduct1, testProduct2);
  }
}
