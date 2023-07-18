package com.example.javaopencommerce.order;

import com.example.javaopencommerce.Amount;
import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.Value;
import com.example.javaopencommerce.order.exceptions.ordervalidation.OrderValueNotMatchingValidationException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.example.javaopencommerce.order.PaymentMethod.MONEY_TRANSFER;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OrderTest {

    @Test
    void shouldCreateValidOrderAndFlushCard() {

        // given
        CardItem cardItem1 = CardItem.withAmount(TestItems.TEST_ITEM_1, Amount.of(1));
        CardItem cardItem2 = CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(3));
        CardItem cardItem3 = CardItem.withAmount(TestItems.TEST_ITEM_3, Amount.of(1));
        Card card = Card.ofProducts(of(cardItem1, cardItem2, cardItem3));

        OrderPrincipal principal = new OrderPrincipal(UUID.randomUUID(), UUID.randomUUID(), MONEY_TRANSFER.name());

        // when
        Order order = card.createOrderFor(principal);

        // then
        assertThat(card.getCardItems()).isEmpty();

        OrderSnapshot snapshot = order.getSnapshot();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(snapshot.getOrderBody())
                    .hasSize(3);
            softly.assertThat(snapshot.getOrderBody())
                    .extracting("id")
                    .contains(TestItems.TEST_ITEM_1.getId(), TestItems.TEST_ITEM_2.getId(), TestItems.TEST_ITEM_3.getId());
            softly.assertThat(snapshot.getOrderValueGross()
                            .asDecimal())
                    .isEqualTo(BigDecimal.valueOf(41.98));
            softly.assertThat(snapshot.getOrderValueNett()
                            .asDecimal())
                    .isEqualTo(BigDecimal.valueOf(34.14));
            softly.assertThat(snapshot.getCreationDate())
                    .isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
            softly.assertThat(snapshot.getId())
                    .isNotNull();
            softly.assertThat(snapshot.getOrderPrincipal())
                    .usingRecursiveAssertion()
                    .isEqualTo(principal.getSnapshot());
            softly.assertThat(snapshot.getOrderStatus())
                    .isEqualTo(OrderStatus.NEW);
        });
    }

    @Test
    void shouldFailOrderValidationOnWrongGrossValue() {

        // given
        OrderPrincipal principal = new OrderPrincipal(UUID.randomUUID(), UUID.randomUUID(), MONEY_TRANSFER.name());

        // when & then
        assertThatThrownBy(() ->
                new Order(
                        OrderId.random(),
                        List.of(Order.OrderItem.fromCardItem(CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(3))
                                .getSnapshot())),
                        principal,
                        PaymentStatus.BEFORE_PAYMENT,
                        OrderStatus.NEW,
                        Value.of(29.98),
                        Value.of(29.98),
                        Instant.now()))
                .isInstanceOf(OrderValueNotMatchingValidationException.class)
                .hasMessageContaining("gross");
    }

    @Test
    void shouldFailOrderValidationOnWrongNettValue() {

        // given
        OrderPrincipal principal = new OrderPrincipal(UUID.randomUUID(), UUID.randomUUID(), MONEY_TRANSFER.name());

        // when & then
        assertThatThrownBy(() ->
                new Order(
                        OrderId.random(),
                        List.of(Order.OrderItem.fromCardItem(CardItem.withAmount(TestItems.TEST_ITEM_2, Amount.of(3))
                                .getSnapshot())),
                        principal,
                        PaymentStatus.BEFORE_PAYMENT,
                        OrderStatus.NEW,
                        Value.of(29.97),
                        Value.of(11.11),
                        Instant.now()))
                .isInstanceOf(OrderValueNotMatchingValidationException.class)
                .hasMessageContaining("nett");
    }
}
