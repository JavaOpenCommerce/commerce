package com.example.opencommerce.adapters.database.catalog.sql;

import com.example.opencommerce.app.catalog.query.ItemFamilyMemberDto;
import com.example.opencommerce.app.catalog.query.ItemFamilyQueryRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@ApplicationScoped
class ItemFamilyQueryRepositoryImpl implements ItemFamilyQueryRepository {

    private final PsqlItemFamilyRepository psqlRepository;

    ItemFamilyQueryRepositoryImpl(PsqlItemFamilyRepository psqlRepository) {
        this.psqlRepository = psqlRepository;
    }

    @Override
    public List<ItemFamilyMemberDto> findItemFamily(Long itemId) {
        return psqlRepository.findFamilyForItemWithId(itemId)
                .map(family -> family.getFamily()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(this::toDto)
                        .toList())
                .orElse(emptyList());
    }

    private ItemFamilyMemberDto toDto(ItemFamilyMember entity) {
        return new ItemFamilyMemberDto(entity.getItemId(), entity.getVariantName());
    }
}
