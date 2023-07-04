package com.example.javaopencommerce.order;

import com.example.javaopencommerce.order.dtos.CardDto;
import com.example.javaopencommerce.order.dtos.CreateOrderDto;
import com.example.javaopencommerce.order.dtos.OrderDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderFacade {


    private final CardRepository cardRepository;
    private final OrderRepository orderRepository;

    public OrderFacade(CardRepository cardRepository,
                       OrderRepository orderRepository) {
        this.cardRepository = cardRepository;
        this.orderRepository = orderRepository;
    }

    public OrderDto createOrder(CreateOrderDto createOrderDto, String cardId) {
        CardDto orderCard = createOrderDto.getCard();

        Card card = Card.validateAndRecreate(CardMapper.toSnapshot(orderCard),
                this.cardRepository.getCardList(cardId));

        OrderPrincipal orderPrincipal = new OrderPrincipal(createOrderDto.getUserId(),
                createOrderDto.getAddressId(), createOrderDto.getPaymentMethod());

        Order order = card.createOrderFor(
                orderPrincipal); // TODO this should emit events to drop stocks
        orderRepository.saveOrder(order);

        return OrderMapper.toDto(order.getSnapshot());
    }
}
