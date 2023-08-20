package com.example.opencommerce.domain.order;

import com.example.opencommerce.domain.Amount;
import com.example.opencommerce.domain.Value;
import com.example.opencommerce.domain.order.CardItem.CardItemSnapshot;
import com.example.opencommerce.domain.order.CardItemStatus.Problem;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class CardTest {

    @Test
    void shouldCreateCardWithProperContentAndPriceCalculation() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));
        CardItem cardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(3));
        CardItem cardItem3 = CardItem.withAmount(TestItems.TEST_ITEM_3, Amount.of(1));

        List<CardItem> cardItemList = List.of(cardItem1, cardItem2, cardItem3);

        // when
        Card card = Card.ofProducts(cardItemList);

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(3);
            softly.assertThat(snapshot.cardValueGross())
                    .isEqualTo(Value.of(BigDecimal.valueOf(51.99)));
            softly.assertThat(snapshot.cardValueNett())
                    .isEqualTo(Value.of(BigDecimal.valueOf(42.28)));
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldAddNewItemToCardIncreaseItsAmountAndRecalculatePrice() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.addItem(TestItems.TEST_ITEM_2, Amount.of(1));
        card.changeItemAmount(TestItems.TEST_ITEM_2, Amount.of(2));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(2);
            softly.assertThat(snapshot.cardValueGross())
                    .isEqualTo(Value.of(BigDecimal.valueOf(4000, 2)));
            softly.assertThat(snapshot.cardValueNett())
                    .isEqualTo(Value.of(BigDecimal.valueOf(32.52)));
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldIncreaseItemAmountWhenAddingAlreadyExistingItem() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.addItem(TestItems.TEST_ITEM_1, Amount.of(2));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(1);
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.amount()
                                    .asInteger())
                            .isEqualTo(4));
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldAddNewItemToCardByIncreasingItsAmount() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.changeItemAmount(TestItems.TEST_ITEM_2, Amount.of(2));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(2);
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.amount()
                                    .asInteger())
                            .isEqualTo(2));
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldReduceAmountOfItemInCard() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.changeItemAmount(TestItems.TEST_ITEM_1, Amount.of(1));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(1);
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.amount()
                                    .asInteger())
                            .isEqualTo(1));
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldRemoveItemAndRecalculatePrice() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));
        CardItem cardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1, cardItem2);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.removeItem(cardItem1.id());

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(1);
            softly.assertThat(snapshot.cardValueGross())
                    .isEqualTo(Value.of(BigDecimal.valueOf(19.98)));
            softly.assertThat(snapshot.cardValueNett())
                    .isEqualTo(Value.of(BigDecimal.valueOf(16.24)));
            softly.assertThat(snapshot.items()
                            .get(0)
                            .id())
                    .isEqualTo(cardItem2.id());
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    @Test
    void shouldRemoveItemByChangingItsAmountToZero() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));
        CardItem cardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1, cardItem2);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.changeItemAmount(TestItems.TEST_ITEM_2, Amount.ZERO);

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(1);
            softly.assertThat(snapshot.cardValueGross())
                    .isEqualTo(Value.of(BigDecimal.valueOf(20.02)));
            softly.assertThat(snapshot.cardValueNett())
                    .isEqualTo(Value.of(BigDecimal.valueOf(16.28)));
            softly.assertThat(snapshot.items()
                            .get(0)
                            .id())
                    .isEqualTo(cardItem1.id());
            snapshot.items()
                    .forEach(item -> softly.assertThat(item.status())
                            .isEqualTo("OK"));
        });
    }

    // non happy paths

    @Test
    void shouldAddNewItemToCardButLimitsItsAmountToStockAmount() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.addItem(TestItems.TEST_ITEM_2, Amount.of(10));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(2);
            CardItemSnapshot changedItem = snapshot.items()
                    .stream()
                    .filter(cardItem -> cardItem.id()
                            .equals(TestItems.TEST_ITEM_2.getId()))
                    .findFirst()
                    .orElseThrow();
            softly.assertThat(changedItem.amount())
                    .isEqualTo(TestItems.TEST_ITEM_2.getStock());
            softly.assertThat(changedItem.status())
                    .isEqualTo(Problem.NOT_ENOUGH_IN_A_STOCK.name());
        });
    }

    @Test
    void shouldIncreaseItemAmountButOnlyToStockMax() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(2));

        List<CardItem> cardItemList = List.of(cardItem1);
        Card card = Card.ofProducts(cardItemList);

        // when
        card.changeItemAmount(TestItems.TEST_ITEM_1, Amount.of(10));

        // then
        CardSnapshot snapshot = card.getSnapshot();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.items())
                    .hasSize(1);
            CardItemSnapshot changedItem = snapshot.items()
                    .stream()
                    .filter(cardItem -> cardItem.id()
                            .equals(TestItems.TEST_ITEM_1.getId()))
                    .findFirst()
                    .orElseThrow();
            softly.assertThat(changedItem.amount())
                    .isEqualTo(TestItems.TEST_ITEM_1.getStock());
            softly.assertThat(changedItem.status())
                    .isEqualTo(Problem.NOT_ENOUGH_IN_A_STOCK.name());
        });
    }

    // TODO test validateAndRecreate method.
}
