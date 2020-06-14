package com.example.business.services;

import com.example.business.converters.AddressConverter;
import com.example.business.converters.ItemConverter;
import com.example.business.models.AddressModel;
import com.example.business.models.ItemModel;
import com.example.database.dao.AddressDAO;
import com.example.database.dao.ItemDAO;
import com.example.database.entity.Address;
import com.example.database.entity.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class CardService {

    private final ItemDAO itemDAO;
    private final AddressDAO addressDAO;


    public CardService(ItemDAO itemDAO, AddressDAO addressDAO) {this.itemDAO = itemDAO;
        this.addressDAO = addressDAO;
    }

    public ItemModel getItemModel(Long id) {
        Item item = itemDAO.getById(id)
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));

        if (item.getStock() < 1) {
            //todo handling
        }
        return ItemConverter
                .convertToModel(item);
    }

    public int checkItemStock(Long id) {
        if (itemDAO.exists(id)) {
            return itemDAO.getItemStock(id);
        } else {
            throw new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND);
        }
    }

    public AddressModel getAddressModel(Long id) {
        Address address = addressDAO.getById(id)
                .orElseThrow(() ->
                        new WebApplicationException("Address with id " + id + " not found", Response.Status.NOT_FOUND));

        return AddressConverter
                .convertToModel(address);
    }
}
