package com.example.opencommerce.app.catalog.query;

import java.util.List;

public interface ItemFamilyQueryRepository {

    List<ItemFamilyMemberDto> findItemFamily(Long itemId);
}
