package com.example.opencommerce.adapters.database.catalog.sql;


import java.util.Optional;

interface PsqlItemFamilyRepository {

    Optional<ItemFamily> findFamilyForItemWithId(Long id);
}
