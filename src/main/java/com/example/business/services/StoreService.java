package com.example.business.services;

import com.example.business.converters.CategoryConverter;
import com.example.business.converters.ItemConverter;
import com.example.business.models.CategoryModel;
import com.example.business.models.ItemModel;
import com.example.database.dao.CategoryDAO;
import com.example.database.dao.ItemDAO;
import com.example.database.entity.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoreService {

    private final ItemDAO itemDAO;
    private final CategoryDAO categoryDAO;


    public StoreService(ItemDAO itemDAO, CategoryDAO categoryDAO) {
        this.itemDAO = itemDAO;
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryModel> getCategoryList() {
        return categoryDAO.getAll().stream()
                .map(c -> CategoryConverter.convertToModel(c))
                .filter(cm -> !cm.getCategoryName().equals("Shipping"))
                .collect(Collectors.toList());
    }

    public ItemModel getItemModel(Long id) {
        Item item = itemDAO.getById(id)
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));

        return ItemConverter
                .convertToModel(item);
    }

}
