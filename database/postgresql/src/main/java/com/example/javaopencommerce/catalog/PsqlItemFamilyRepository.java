package com.example.javaopencommerce.catalog;

import java.util.Optional;

interface PsqlItemFamilyRepository {

    Optional<ItemFamily> findFamilyForItemWithId(Long id);
}
