package com.example.opencommerce.domain.pricing;

import com.example.opencommerce.domain.ItemId;
import com.example.opencommerce.domain.OperationResult;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.Vat;
import com.example.opencommerce.domain.pricing.events.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ItemPriceTest {

    private static final ItemId ITEM_ID = ItemId.random();
    private static final Vat VAT = Vat.of(0.23);
    private static final Instant NOW = Instant.now();

    @Test
    void shouldCreateNewPriceWithZeroedParams() {

        // when
        ItemPrice itemPrice = ItemPrice.create(ITEM_ID);

        // then
        PriceSnapshot snapshot = itemPrice.getSnapshot();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.getBasePriceNett())
                    .isEqualTo(Value.ZERO);
            softly.assertThat(snapshot.getBasePriceGross())
                    .isEqualTo(Value.ZERO);
            softly.assertThat(snapshot.getDiscount())
                    .isNull();
        });
    }

    @Test
    void shouldInitiatePriceAndChangeItsValue() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        Value baseValue = Value.of(10.01);
        Value newValue = Value.of(11.11);

        // when
        OperationResult<NewPriceInitiatedEvent> initiationResult = price.initiate(baseValue, VAT);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(initiationResult.successful())
                    .isTrue();
            NewPriceInitiatedEvent result = initiationResult.result();
            softly.assertThat(result.getBasePrice())
                    .isEqualTo(baseValue.asDecimal());
            softly.assertThat(result.getVat())
                    .isEqualTo(VAT.asDecimal());
        });

        // when
        OperationResult<BasePriceChangedEvent> changePriceResult = price.changeBasePrice(newValue, NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(changePriceResult.successful())
                    .isTrue();
            BasePriceChangedEvent result = changePriceResult.result();
            softly.assertThat(result.getNewPrice())
                    .isEqualTo(newValue.asDecimal());
            softly.assertThat(result.getValidFrom())
                    .isEqualTo(NOW);
        });
    }

    @Test
    void shouldApplyProperDiscountAndFindTheLowestPriceWithinLastMonth() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        Value firstChange = Value.of(10.00);
        Value secondChange = Value.of(20.00);
        Value thirdChange = Value.of(15.00);
        Value fourthChange = Value.of(17.00);
        price.initiate(Value.of(22.00), VAT);

        // when
        price.changeBasePrice(firstChange, NOW.minus(38L, ChronoUnit.DAYS));
        price.changeBasePrice(secondChange, NOW.minus(31L, ChronoUnit.DAYS));
        price.changeBasePrice(thirdChange, NOW.minus(20L, ChronoUnit.DAYS));
        price.changeBasePrice(fourthChange, NOW.minus(10L, ChronoUnit.DAYS));

        price.addNewDiscount(Value.of(BigDecimal.ONE), NOW);

        // then
        PriceSnapshot snapshot = price.getSnapshot();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.getBasePriceNett())
                    .isEqualTo(Value.of(17.00));
            softly.assertThat(snapshot.getDiscount()
                            .discountedPriceNett())
                    .isEqualTo(Value.of(16.00));
            softly.assertThat(snapshot.getDiscount()
                            .lowestPriceBeforeDiscountGross())
                    .isEqualTo(thirdChange.toGross(VAT));
        });
    }

    @Test
    void shouldApplyAndThenRemoveDiscount() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(22.00), VAT);
        price.addNewDiscount(Value.of(BigDecimal.ONE), NOW);

        // when
        OperationResult<DiscountRemovedEvent> removalResult = price.removeCurrentDiscount(NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(removalResult.successful())
                    .isTrue();
            PriceSnapshot snapshot = price.getSnapshot();
            softly.assertThat(snapshot.getDiscount())
                    .isNull();
            softly.assertThat(snapshot.getBasePriceNett())
                    .isEqualTo(Value.of(22.00));
        });
    }

    @Test
    void shouldRecreatePriceBasingOnEvents() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        NewPriceInitiatedEvent initEvent = new NewPriceInitiatedEvent(Value.of(1.15), VAT);
        BasePriceChangedEvent changeEvent = new BasePriceChangedEvent(NOW, Value.of(2.25));
        DiscountAppliedEvent discountApplyEvent = new DiscountAppliedEvent(NOW, Value.of(0.10));
        List<PriceEvent> history = List.of(initEvent, changeEvent, discountApplyEvent);

        // when
        price.when(history);

        // then
        PriceSnapshot snapshot = price.getSnapshot();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.getBasePriceNett())
                    .isEqualTo(Value.of(2.25));
            softly.assertThat(snapshot.getDiscount()
                            .discountedPriceNett())
                    .isEqualTo(Value.of(2.15));
            softly.assertThat(snapshot.getDiscount()
                            .lowestPriceBeforeDiscountGross())
                    .isEqualTo(Value.of(1.41));
        });
    }

    @Test
    void shouldFailWhenTryingToInitPriceMoreThanOnce() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(22.00), VAT);

        // when
        OperationResult<NewPriceInitiatedEvent> secondInitiateResult = price.initiate(Value.of(21.00), VAT);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(secondInitiateResult.successful())
                    .isFalse();
            softly.assertThat(price.getSnapshot()
                            .getBasePriceNett())
                    .isEqualTo(Value.of(22.00));
        });
    }

    @Test
    void shouldFailChangePriceWhenNewPriceIsNegative() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(22.00), VAT);

        // when
        OperationResult<BasePriceChangedEvent> changeResult = price.changeBasePrice(Value.of(BigDecimal.valueOf(-5.00)), NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(changeResult.successful())
                    .isFalse();
            softly.assertThat(price.getSnapshot()
                            .getBasePriceNett())
                    .isEqualTo(Value.of(22.00));
        });
    }

    @Test
    void shouldFailChangePriceWhenNewPriceAfterDiscountIsNegative() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(22.00), VAT);
        price.addNewDiscount(Value.of(10.00), NOW);

        // when
        OperationResult<BasePriceChangedEvent> changeResult = price.changeBasePrice(Value.of(BigDecimal.valueOf(9.99)), NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(changeResult.successful())
                    .isFalse();
            softly.assertThat(changeResult.getErrors())
                    .element(0)
                    .isEqualTo("New price: 9.99 with current discount: 10.00 applied is negative: -0.01!");
            PriceSnapshot snapshot = price.getSnapshot();
            softly.assertThat(snapshot.getBasePriceNett())
                    .isEqualTo(Value.of(22.00));
            softly.assertThat(snapshot.getDiscount()
                            .discountedPriceNett())
                    .isEqualTo(Value.of(12.00));
        });
    }

    @Test
    void shouldFailApplyingDiscountWhenItsValueIsNegative() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(22.00), VAT);

        // when
        OperationResult<DiscountAppliedEvent> result = price.addNewDiscount(Value.of(BigDecimal.valueOf(-5.00)), NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.successful())
                    .isFalse();
            softly.assertThat(price.getSnapshot()
                            .getBasePriceNett())
                    .isEqualTo(Value.of(22.00));
        });
    }

    @Test
    void shouldFailApplyingDiscountWhenFinalPriceAfterDiscountIsNegative() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        price.initiate(Value.of(31.00), VAT);

        // when
        OperationResult<DiscountAppliedEvent> result = price.addNewDiscount(Value.of(BigDecimal.valueOf(31.05)), NOW);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.successful())
                    .isFalse();
            softly.assertThat(result.getErrors())
                    .element(0)
                    .isEqualTo("Discount value: 31.05 is higher than current base price: 31.00!");
            softly.assertThat(price.getSnapshot()
                            .getBasePriceNett())
                    .isEqualTo(Value.of(31.00));
        });
    }

    @Test
    void shouldFailPriceRecreationWhenAnyEventPlaybackIsFailing() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        NewPriceInitiatedEvent initEvent = new NewPriceInitiatedEvent(Value.of(1.15), VAT);
        BasePriceChangedEvent changeEvent = new BasePriceChangedEvent(NOW, Value.of(2.25));
        DiscountAppliedEvent discountApplyEvent = new DiscountAppliedEvent(NOW, Value.of(0.10));
        List<PriceEvent> history = List.of(changeEvent, initEvent, discountApplyEvent);

        // when & then
        assertThatThrownBy(() -> price.when(history)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Price not initiated yet!");
    }

    @Test
    void shouldFailPriceRecreationWhenUnknownEvent() {

        // given
        ItemPrice price = ItemPrice.create(ITEM_ID);
        WeirdPriceEvent unknownEvent = new WeirdPriceEvent();
        List<PriceEvent> history = List.of(unknownEvent);

        // when & then
        assertThatThrownBy(() -> price.when(history)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Unknown price event type: WeirdPriceEvent");
    }

    private static class WeirdPriceEvent extends PriceEvent {
        @Override
        public Instant getValidFrom() {
            return null;
        }
    }
}
