package com.example.javaopencommerce.catalog.query;

import java.util.List;

public interface ItemFamilyQueryRepository {

    List<ItemFamilyMemberDto> findItemFamily(Long itemId);
}
