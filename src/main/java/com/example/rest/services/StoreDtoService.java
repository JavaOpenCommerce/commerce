package com.example.rest.services;

import com.example.database.services.StoreService;
import com.example.rest.dtos.CategoryDto;
import com.example.rest.dtos.PageDto;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemPageConverter;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoreDtoService {

    private final StoreService storeService;

    public StoreDtoService(StoreService storeService) {this.storeService = storeService;}

    public PageDto getItemsPageByCategory(Long categoryId, int pageIndex, int pageSize) {
        return ItemPageConverter
                .convertToDto(storeService.getItemsPageByCategory(categoryId, pageIndex, pageSize));
    }

    public PageDto getItemsPageByProducer(Long producerId, int pageIndex, int pageSize) {
        return ItemPageConverter
                .convertToDto(storeService.getItemsPageByProducer(producerId, pageIndex, pageSize));
    }

    public List<CategoryDto> getCategoryList() {
        return storeService.getCategoryList().stream()
                .map(c -> CategoryConverter.convertToDto(c))
                .collect(Collectors.toList());
    }
}
