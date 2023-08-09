package com.example.javaopencommerce.catalog.query;

import com.example.javaopencommerce.catalog.query.ItemFamilyMemberDto;

import java.util.List;

public interface ItemFamilyQueryRepository {

    List<ItemFamilyMemberDto> findItemFamily(Long itemId);
}
