package com.example.javaopencommerce.catalog.exceptions;

import static java.lang.String.format;

import com.example.javaopencommerce.catalog.CategoryId;

public class CategoryNotFoundException extends RuntimeException {

  private static final String ERROR_MSG = "Category with id: %s does not exists!";

  public CategoryNotFoundException(CategoryId categoryId) {
    super(format(ERROR_MSG, categoryId.id()));
  }
}
