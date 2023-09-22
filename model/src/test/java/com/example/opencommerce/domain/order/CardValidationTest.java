package com.example.opencommerce.domain.order;


import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.order.exceptions.ordervalidation.OrderValidationException;
import com.example.opencommerce.domain.order.exceptions.ordervalidation.OrderValueNotMatchingValidationException;
import com.example.opencommerce.domain.order.exceptions.ordervalidation.OutOfStockItemsValidationException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;

class CardValidationTest {

    @Test
    void shouldPassValidation() {

        // given
        CardItem testCardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(1));
        CardItem testCardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(1));
        CardItem testCardItem3 = CardItem.empty(ItemId.of(3L), "test");

        CardSnapshot cardToRecreate = new CardSnapshot(
                List.of(testCardItem1.getSnapshot(), testCardItem2.getSnapshot(), testCardItem3.getSnapshot()),
                Value.of(BigDecimal.valueOf(16.26)),
                Value.of(BigDecimal.valueOf(2000L, 2)));

        // when & then
        assertThatCode(() -> Card.validateAndRecreate(cardToRecreate,
                List.of(testCardItem1, testCardItem2, testCardItem3))).doesNotThrowAnyException();
    }

    @Test
    void shouldNotPassValidationBecauseOfNonMatchingOrderAndCardSize() {

        // given
        CardItem testCardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(1));
        CardItem testCardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(1));

        CardSnapshot cardToRecreate = new CardSnapshot(
                List.of(testCardItem1.getSnapshot()), Value.of(BigDecimal.ONE),
                Value.of(BigDecimal.ONE));

        // when
        Throwable throwable = catchThrowable(
                () -> Card.validateAndRecreate(cardToRecreate, List.of(testCardItem1, testCardItem2)));

        OrderValidationException validationException = (OrderValidationException) throwable;

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(validationException.getDerivativeExceptions())
                    .hasSize(0);
            softly.assertThat(validationException.getMessage())
                    .contains("Order body is different than actual card body!");
        });
    }

    @Test
    void shouldNotPassValidationBecauseOfNonMatchingGrossPrice() {

        // given
        CardItem testCardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(1));
        CardItem testCardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(1));

        CardSnapshot cardToRecreate = new CardSnapshot(
                List.of(testCardItem1.getSnapshot(), testCardItem2.getSnapshot()), Value.of(BigDecimal.ONE),
                Value.of(BigDecimal.ONE));

        // when
        Throwable throwable = catchThrowable(
                () -> Card.validateAndRecreate(cardToRecreate, List.of(testCardItem1, testCardItem2)));

        OrderValidationException validationException = (OrderValidationException) throwable;

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(validationException.getDerivativeExceptions())
                    .hasSize(2);
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(0))
                    .isInstanceOf(
                            OrderValueNotMatchingValidationException.class);
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(0)
                            .getMessage())
                    .contains("20.00");
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(1))
                    .isInstanceOf(
                            OrderValueNotMatchingValidationException.class);
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(1)
                            .getMessage())
                    .contains("16.26");
        });
    }

    @Test
    void shouldNotPassValidationBecauseOfExceededStock() {

        // given
        CardItem testCardItem = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(10));
        CardItem.CardItemSnapshot testCardItemSnapshot = testCardItem.getSnapshot();
        CardItem.CardItemSnapshot testItemModifiedStock = new CardItem.CardItemSnapshot(
                testCardItemSnapshot.itemSnapshot(),
                Value.of(81.38),
                Value.of(100.10),
                Amount.of(10),
                ""
        );

        CardSnapshot cardToRecreate = new CardSnapshot(
                List.of(testItemModifiedStock),
                Value.of(81.38),
                Value.of(100.10));

        // when
        Throwable throwable = catchThrowable(
                () -> Card.validateAndRecreate(cardToRecreate, List.of(testCardItem)));

        OrderValidationException validationException = (OrderValidationException) throwable;

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(validationException.getDerivativeExceptions())
                    .hasSize(1);
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(0))
                    .isInstanceOf(
                            OutOfStockItemsValidationException.class);
            softly.assertThat(validationException.getDerivativeExceptions()
                            .get(0)
                            .getMessage())
                    .contains("Items exceeding stocks - ids: 1");
        });
    }

    @Test
    void shouldNotPassValidationBecauseOfExceededStockAndPricingMismatches() {

        // given
        CardItem testCardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(10));
        CardItem testCardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(10));

        CardItem.CardItemSnapshot testItemModifiedStock1 = new CardItem.CardItemSnapshot(
                testCardItem1.getSnapshot()
                        .itemSnapshot(),
                Value.of(81.38),
                Value.of(100.10),
                Amount.of(10),
                ""
        );

        CardSnapshot cardToRecreate = new CardSnapshot(
                List.of(testItemModifiedStock1, testCardItem2.getSnapshot()),
                Value.of(BigDecimal.valueOf(277.56)),
                Value.of(BigDecimal.valueOf(341.16)));

        // when
        Throwable throwable = catchThrowable(
                () -> Card.validateAndRecreate(cardToRecreate, List.of(testCardItem1, testCardItem2)));

        OrderValidationException validationException = (OrderValidationException) throwable;

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(validationException.getDerivativeExceptions())
                    .hasSize(3);
        });
    }
}
