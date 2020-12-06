package com.example.rest.services;

import com.example.database.services.StoreService;
import com.example.elasticsearch.SearchRequest;
import com.example.rest.dtos.*;
import com.example.utils.LanguageResolver;
import com.example.utils.converters.CategoryConverter;
import com.example.utils.converters.ItemDetailConverter;
import com.example.utils.converters.ItemPageConverter;
import com.example.utils.converters.ProducerConverter;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class StoreDtoService {

    private final StoreService storeService;
    private final LanguageResolver langRes;

    public StoreDtoService(StoreService storeService, LanguageResolver langRes) {
        this.storeService = storeService;
        this.langRes = langRes;
    }

    public Uni<ItemDetailDto> getItemById(Long id) {
        return storeService
                .getItemById(id).onItem()
                .transform(i -> ItemDetailConverter.convertToDto(i, langRes.getLanguage(), langRes.getDefault()));
    }

    public Uni<PageDto<ItemDto>> getFilteredItems(SearchRequest request) {

        return storeService.getFilteredItemsPage(request).onItem()
                .transform(itemPage -> {
                    PageDto<ItemDto> itemDtoPageDto = ItemPageConverter.convertToDto(itemPage,
                            langRes.getLanguage(),
                            langRes.getDefault());

                    sortItems(itemDtoPageDto.getItems(), request);

                    return itemDtoPageDto;
                });
    }

    public Uni<List<CategoryDto>> getAllCategories() {
        return storeService.getAllCategories().map(categoryModels ->
                categoryModels.stream()
                        .map(cat -> CategoryConverter.convertToDto(cat, langRes.getLanguage(), langRes.getDefault()))
                        .collect(toList()));
    }

    public Uni<List<ProducerDto>> getAllProducers() {
        return storeService.getAllProducers().map(producerModels ->
                producerModels.stream()
                        .map(prod -> ProducerConverter.convertToDto(prod, langRes.getLanguage(), langRes.getDefault()))
                        .collect(toList()));
    }

    private void sortItems(List<ItemDto> itemDtos, SearchRequest request) {

        String sortingType = request.getSortBy().toUpperCase() + "-" + request.getOrder().toUpperCase();

        switch (sortingType) {
            case "VALUE-ASC":
                itemDtos.sort(Comparator.comparing(ItemDto::getValueGross));
                break;
            case "VALUE-DESC":
                itemDtos.sort(Comparator.comparing(ItemDto::getValueGross).reversed());
                break;
            case "NAME-DESC":
                itemDtos.sort(Comparator.comparing(ItemDto::getName).reversed());
                break;
            case "NAME-ASC":
            default:
                itemDtos.sort(Comparator.comparing(ItemDto::getName));
                break;
        }
    }
}
