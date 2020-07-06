package com.example.rest.services;

import com.example.database.services.StoreService;
import com.example.rest.dtos.CategoryDto;
import com.example.rest.dtos.ItemDetailDto;
import com.example.rest.dtos.ItemDto;
import com.example.rest.dtos.PageDto;
import com.example.rest.dtos.ProducerDto;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemDetailConverter;
import com.example.utils.converters.ItemPageConverter;
import com.example.utils.converters.ProducerConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoreDtoService {

    private final StoreService storeService;
    private final LanguageResolver langResolver;

    public StoreDtoService(StoreService storeService, LanguageResolver langResolver) {
        this.storeService = storeService;
        this.langResolver = langResolver;
    }


    public Uni<ItemDetailDto> getItemById(Long id) {
        return storeService
                .getItemById(id)
                .onItem()
                .apply(i -> ItemDetailConverter.convertToDto(i, langResolver.getLanguage(), langResolver.getDefault()));
    }

    public PageDto<ItemDto> getPageOfAllItems(int pageIndex, int pageSize) {
        return ItemPageConverter
                .convertToDto(storeService.getPageOfAllItems(pageIndex, pageSize),
                        langResolver.getLanguage(),
                        langResolver.getDefault());
    }

    public PageDto<ItemDto> getItemsPageByCategory(Long categoryId, int pageIndex, int pageSize) {
        return ItemPageConverter
                .convertToDto(storeService.getItemsPageByCategory(categoryId, pageIndex, pageSize),
                        langResolver.getLanguage(),
                        langResolver.getDefault());
    }

    public PageDto<ItemDto> getItemsPageByProducer(Long producerId, int pageIndex, int pageSize) {
        return ItemPageConverter
                .convertToDto(storeService.getItemsPageByProducer(producerId, pageIndex, pageSize),
                        langResolver.getLanguage(),
                        langResolver.getDefault());
    }

    public List<CategoryDto> getCategoryList() {
        return storeService.getCategoryList().stream()
                .map(c -> CategoryConverter.convertToDto(c, langResolver.getLanguage(), langResolver.getDefault()))
                .collect(Collectors.toList());
    }

    public List<ProducerDto> getProducerList() {
        return storeService.getProducerList().stream()
                .map(c -> ProducerConverter.convertToDto(c, langResolver.getLanguage(), langResolver.getDefault()))
                .collect(Collectors.toList());
    }
}
