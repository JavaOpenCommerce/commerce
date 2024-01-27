package com.example.opencommerce.app.order;

import com.example.opencommerce.app.order.commands.CreateOrderCommand;
import com.example.opencommerce.app.order.query.CardDto;
import com.example.opencommerce.app.warehouse.ReserveItemScenario;
import com.example.opencommerce.domain.OrderId;
import com.example.opencommerce.domain.order.Card;
import com.example.opencommerce.domain.order.CardItem;
import com.example.opencommerce.domain.order.CardRepository;
import com.example.opencommerce.domain.order.Order;
import com.example.opencommerce.domain.order.OrderPrincipal;
import com.example.opencommerce.domain.order.OrderRepository;
import com.example.opencommerce.domain.order.OrderSnapshot;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CreateOrderScenario {


    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;
    private final ReserveItemScenario reserveItemScenario;
    private final CardFactory cardFactory;

    public CreateOrderScenario(CardRepository cardRepository,
                               OrderRepository orderRepository,
                               CardFactory cardFactory,
                               ReserveItemScenario reserveItemScenario) {
        this.cardRepository = cardRepository;
        this.orderRepository = orderRepository;
        this.cardFactory = cardFactory;
        this.reserveItemScenario = reserveItemScenario;
    }

    @Transactional
    public OrderId createOrder(CreateOrderCommand createOrderCommand) {
        CardDto orderCard = createOrderCommand.card();
        String cardId = createOrderCommand.cardId();
        List<CardItem> cardItems = cardFactory.restoreCardBody(this.cardRepository.getCard(cardId));

        Card card = Card.validateAndRecreate(CardMapper.toSnapshot(orderCard), cardItems);

        OrderPrincipal orderPrincipal = new OrderPrincipal(createOrderCommand.userId(),
                createOrderCommand.addressId(), createOrderCommand.paymentMethod());

        Order order = card.createOrderFor(
                orderPrincipal);

        orderRepository.saveOrder(order);

        OrderSnapshot snapshot = order.getSnapshot();
        snapshot.getOrderBody()
                .forEach(item -> reserveItemScenario.reserve(toReservationCommand(item, snapshot.getId())));

        cardRepository.removeCard(cardId);
        return snapshot.getId();
    }

    private ReserveItemScenario.ReserveItemCommand toReservationCommand(Order.OrderItem item, OrderId orderId) {
        return new ReserveItemScenario.ReserveItemCommand(item.getId(), orderId, item.getAmount());
    }
}
