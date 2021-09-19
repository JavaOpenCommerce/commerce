package com.example.javaopencommerce.rest.services;

import com.example.javaopencommerce.order.OrderDetails;
import com.example.javaopencommerce.order.OrderDetailsDto;
import com.example.javaopencommerce.order.OrderDetailsService;
import com.example.javaopencommerce.utils.LanguageResolver;
import com.example.javaopencommerce.utils.converters.OrderDetailsConverter;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderDetailsDtoService {

    private final OrderDetailsService orderDetailsService;
    private final LanguageResolver languageResolver;

    public OrderDetailsDtoService(OrderDetailsService orderDetailsService, LanguageResolver languageResolver) {
        this.orderDetailsService = orderDetailsService;
        this.languageResolver = languageResolver;
    }

    public Uni<OrderDetailsDto> getOrderDetailsById(Long id) {
        return orderDetailsService.getOrderDetailsById(id).map(od ->
                OrderDetailsConverter
                    .convertToDto(od, languageResolver.getLanguage(), languageResolver.getDefault()));
    }

    public Uni<OrderDetailsDto> saveOrderDetails(Uni<OrderDetailsDto> orderDetailsDtoUni) {

        Uni<OrderDetails> savedDetailsUni = orderDetailsService.saveOrderDetails(
                orderDetailsDtoUni.map(OrderDetailsConverter::convertDtoToModel)
        );

        return savedDetailsUni.map(od ->
                OrderDetailsConverter.convertToDto(od, languageResolver.getLanguage(), languageResolver.getDefault())
        );
    }


}
