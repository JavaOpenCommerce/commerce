package com.example.database.repositories.interfaces;

import com.example.database.entity.Category;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CategoryRepository {

    Uni<List<Category>> getAll();

    Uni<List<Category>> getCategoriesByItemId(Long id);

}
