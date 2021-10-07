package com.example.javaopencommerce.order;

import com.example.javaopencommerce.item.ItemFacade;
import com.example.javaopencommerce.order.dtos.OrderDetailsDto;
import io.smallrye.mutiny.Uni;


public class OrderFacade {

    private final OrderDetailsService orderDetailsService;
    private final OrderIntegrityValidator integrityValidator;
    private final OrderFactory orderFactory;
    private final OrderDtoFactory orderDtoFactory;
    private final ItemFacade itemFacade;

    public OrderFacade(OrderDetailsService orderDetailsService,
        OrderIntegrityValidator integrityValidator,
        OrderFactory orderFactory,
        OrderDtoFactory orderDtoFactory, ItemFacade itemFacade) {
        this.orderDetailsService = orderDetailsService;
        this.integrityValidator = integrityValidator;
        this.orderFactory = orderFactory;
        this.orderDtoFactory = orderDtoFactory;
        this.itemFacade = itemFacade;
    }

    public Uni<OrderDetailsDto> makeOrder(OrderDetailsDto orderDetailsDto) {
        integrityValidator.validateOrder(orderDetailsDto).subscribe();
        itemFacade.updateItemStocks(orderDetailsDto.getCard().getProducts());

        OrderDetails orderDetails = orderFactory.toOrderModel(orderDetailsDto);
        return orderDetailsService.saveOrderDetails(orderDetails)
            .flatMap(orderDtoFactory::toDto);
    }
}
