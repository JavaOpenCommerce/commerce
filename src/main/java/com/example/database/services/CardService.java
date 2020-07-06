package com.example.database.services;

import com.example.business.models.AddressModel;
import com.example.business.models.ItemModel;
import com.example.database.entity.Address;
import com.example.database.entity.Item;
import com.example.database.repositories.interfaces.AddressRepository;
import com.example.database.repositories.interfaces.ItemRepository;
import com.example.utils.converters.AddressConverter;
import com.example.utils.converters.ItemConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class CardService {

    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;

    public CardService(ItemRepository itemRepository,
            AddressRepository addressRepository) {
        this.itemRepository = itemRepository;
        this.addressRepository = addressRepository;
    }

    public ItemModel getItemModel(Long id) {
        Item item = Optional.ofNullable(new Item()) //TODO
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));

        return ItemConverter
                .convertToModel(item);
    }

    public int checkItemStock(Long id) {
        Item item = Optional.ofNullable(new Item()) //TODO
                .orElseThrow(() ->
                        new WebApplicationException("Item with id " + id + " not found", Response.Status.NOT_FOUND));

        if (item.getStock() < 1) {
            //todo handling, issue #6
        }

        return item.getStock();
    }

    public AddressModel getAddressModel(Long id) {
        Address address = Optional.ofNullable(new Address()) //TODO
                .orElseThrow(() ->
                        new WebApplicationException("Address with id " + id + " not found", Response.Status.NOT_FOUND));

        return AddressConverter
                .convertToModel(address);
    }

    public List<ItemModel> getShippingMethods() {
        return itemRepository.getShippingMethodList().stream()
                .map(i -> ItemConverter.convertToModel(i))
                .collect(Collectors.toList());

    }
}
