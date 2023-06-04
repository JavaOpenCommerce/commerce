package com.example.javaopencommerce.item;

import com.example.javaopencommerce.item.dtos.ProductDto;
import java.util.List;

public class ItemFacade {

  private final ItemService itemService;

  public ItemFacade(ItemService itemService) {
    this.itemService = itemService;
  }

  public void updateItemStocks(List<ProductDto> products) {
    products.forEach(product ->
        this.itemService.changeStock(product.getItem().getId(), product.getAmount()));
  }

  //ITEM CRUDS HERE!!
}
