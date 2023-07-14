package com.example.javaopencommerce.catalog;

import com.example.javaopencommerce.catalog.dtos.ItemFamilyMemberDto;

import java.util.List;

public interface ItemFamilyQueryRepository {

    List<ItemFamilyMemberDto> findItemFamily(Long itemId);
}
