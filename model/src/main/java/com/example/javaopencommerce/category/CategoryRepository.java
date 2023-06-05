package com.example.javaopencommerce.category;

import java.util.List;

interface CategoryRepository {

  List<Category> getAll();

  List<Category> getCategoriesForItem(Long id);

  List<Category> getCategoriesForItems(List<Long> ids);

}
