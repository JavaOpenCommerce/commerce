package com.example.rest.services;

import com.example.business.models.OrderDetailsModel;
import com.example.database.services.OrderDetailsService;
import com.example.rest.dtos.OrderDetailsDto;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.OrderDetailsConverter;
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
                OrderDetailsConverter.convertToDto(od, languageResolver.getLanguage(), languageResolver.getDefault()));
    }

    public Uni<OrderDetailsDto> saveOrderDetails(Uni<OrderDetailsDto> orderDetailsDtoUni) {

        Uni<OrderDetailsModel> savedDetailsUni = orderDetailsService.saveOrderDetails(
                orderDetailsDtoUni.map(OrderDetailsConverter::convertDtoToModel)
        );

        return savedDetailsUni.map(od ->
                OrderDetailsConverter.convertToDto(od, languageResolver.getLanguage(), languageResolver.getDefault())
        );
    }


}
