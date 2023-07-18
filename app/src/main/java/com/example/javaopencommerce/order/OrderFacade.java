package com.example.javaopencommerce.order;

import com.example.javaopencommerce.OrderId;
import com.example.javaopencommerce.order.dtos.CardDto;
import com.example.javaopencommerce.order.dtos.CreateOrderDto;
import com.example.javaopencommerce.warehouse.ReserveItemScenario;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class OrderFacade {


    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;
    private final ReserveItemScenario reserveItemScenario;
    private final CardFactory cardFactory;

    public OrderFacade(CardRepository cardRepository,
                       OrderRepository orderRepository,
                       CardFactory cardFactory,
                       ReserveItemScenario reserveItemScenario) {
        this.cardRepository = cardRepository;
        this.orderRepository = orderRepository;
        this.cardFactory = cardFactory;
        this.reserveItemScenario = reserveItemScenario;
    }

    @Transactional
    public OrderId createOrder(CreateOrderDto createOrderDto, String cardId) {
        CardDto orderCard = createOrderDto.card();
        List<CardItem> cardItems = cardFactory.restoreCard(this.cardRepository.getCard(cardId))
                .getCardItems();
        Card card = Card.validateAndRecreate(CardMapper.toSnapshot(orderCard), cardItems);

        OrderPrincipal orderPrincipal = new OrderPrincipal(createOrderDto.userId(),
                createOrderDto.addressId(), createOrderDto.paymentMethod());

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
