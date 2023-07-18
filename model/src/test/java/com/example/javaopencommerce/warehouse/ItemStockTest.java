package com.example.javaopencommerce.warehouse;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.ItemId;
import com.example.javaopencommerce.OperationResult;
import com.example.javaopencommerce.OrderId;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ItemStockTest {

    @Test
    void shouldAddItemsToEmptyStock() {

        // given
        ItemId itemId = ItemId.of(1L);
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(10);

        // when
        OperationResult operationResult = itemStock.increaseStockBy(stockToAdd);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(operationResult.succesful())
                    .isTrue();
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.reservations())
                    .isEmpty();
        });
    }

    @Test
    void shouldReserveItemStockForTwoOrders() {

        // given
        ItemId itemId = ItemId.of(1L);
        OrderId orderId1 = OrderId.random();
        OrderId orderId2 = OrderId.random();
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(15);

        // when
        OperationResult stockIncreaseResult = itemStock.increaseStockBy(stockToAdd);
        OperationResult stockReservationResult1 = itemStock.makeStockReservation(ItemReservation.newReservation(orderId1, Amount.of(3)));
        OperationResult stockReservationResult2 = itemStock.makeStockReservation(ItemReservation.newReservation(orderId2, Amount.of(5)));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(stockIncreaseResult.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult1.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult2.succesful())
                    .isTrue();
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(Amount.of(7));
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.reservations())
                    .hasSize(2);
        });
    }

    @Test
    void shouldReserveItemStockAndExecuteIt() {

        // given
        ItemId itemId = ItemId.of(1L);
        OrderId orderId = OrderId.random();
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(15);

        // when
        OperationResult stockIncreaseResult = itemStock.increaseStockBy(stockToAdd);
        OperationResult stockReservationResult = itemStock.makeStockReservation(ItemReservation.newReservation(orderId, Amount.of(10)));
        OperationResult executionResult = itemStock.executeReservationFromOrderWithId(orderId);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(stockIncreaseResult.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult.succesful())
                    .isTrue();
            softly.assertThat(executionResult.succesful())
                    .isTrue();
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(Amount.of(5));
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(Amount.of(5));
            softly.assertThat(itemStock.reservations())
                    .isEmpty();
        });
    }

    @Test
    void shouldReserveItemStockAndCancelIt() {

        // given
        ItemId itemId = ItemId.of(1L);
        OrderId orderId = OrderId.random();
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(15);

        // when
        OperationResult stockIncreaseResult = itemStock.increaseStockBy(stockToAdd);
        OperationResult stockReservationResult = itemStock.makeStockReservation(ItemReservation.newReservation(orderId, Amount.of(10)));
        OperationResult cancellationResult = itemStock.cancelReservationFromOrderWithId(orderId);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(stockIncreaseResult.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult.succesful())
                    .isTrue();
            softly.assertThat(cancellationResult.succesful())
                    .isTrue();
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(Amount.of(15));
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(Amount.of(15));
            softly.assertThat(itemStock.reservations())
                    .isEmpty();
        });
    }

    @Test
    void shouldFailOnSecondReservationBecauseOfExceededStock() {

        // given
        ItemId itemId = ItemId.of(1L);
        OrderId orderId1 = OrderId.random();
        OrderId orderId2 = OrderId.random();
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(10);

        // when
        OperationResult stockIncreaseResult = itemStock.increaseStockBy(stockToAdd);
        OperationResult stockReservationResult1 = itemStock.makeStockReservation(ItemReservation.newReservation(orderId1, Amount.of(8)));
        OperationResult stockReservationResult2 = itemStock.makeStockReservation(ItemReservation.newReservation(orderId2, Amount.of(3)));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(stockIncreaseResult.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult1.succesful())
                    .isTrue();
            softly.assertThat(stockReservationResult2.succesful())
                    .isFalse();
            softly.assertThat(stockReservationResult2.getErrors())
                    .element(0)
                    .isEqualTo("Not enough free items in a stock! Requested: 3, available: 2.");
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(Amount.of(2));
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.reservations())
                    .hasSize(1);
        });
    }

    @Test
    void shouldFailWhenTryingToExecuteNonExistingReservation() {

        // given
        ItemId itemId = ItemId.of(1L);
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(10);

        // when
        itemStock.increaseStockBy(stockToAdd);
        OperationResult operationResult = itemStock.executeReservationFromOrderWithId(OrderId.random());

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(operationResult.succesful())
                    .isFalse();
            softly.assertThat(itemStock.freeStock())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.quantityOnHand())
                    .isEqualTo(stockToAdd);
            softly.assertThat(itemStock.reservations())
                    .isEmpty();
        });
    }

    @Test
    void shouldFailWhenTryingToExecuteReservationButQuantityOnHandIsTooLow() throws NoSuchFieldException, IllegalAccessException {

        // given
        ItemId itemId = ItemId.of(1L);
        OrderId orderId = OrderId.random();
        ItemStock itemStock = ItemStock.empty(itemId);
        Amount stockToAdd = Amount.of(10);

        // when
        itemStock.increaseStockBy(stockToAdd);
        itemStock.makeStockReservation(ItemReservation.newReservation(orderId, Amount.of(5)));

        Field quantityOnHand = itemStock.getClass()
                .getDeclaredField("quantityOnHand");
        quantityOnHand.setAccessible(true);
        quantityOnHand.set(itemStock, Amount.of(4));

        OperationResult operationResult = itemStock.executeReservationFromOrderWithId(orderId);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(operationResult.succesful())
                    .isFalse();
            softly.assertThat(operationResult.getErrors())
                    .element(0)
                    .isEqualTo("Stock is too low, cant execute! Current stock: 4, Reserved: 5.");
            softly.assertThat(itemStock.reservations())
                    .hasSize(1);
        });
    }
}
