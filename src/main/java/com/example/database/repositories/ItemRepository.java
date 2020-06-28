package com.example.database.repositories;

import com.example.database.entity.Item;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {

    public PanacheQuery<Item> listItemByCategoryId(Long categoryId, int pageIndex, int pageSize) {

        PanacheQuery<Item> page = find("SELECT i FROM Item i INNER JOIN i.category c WHERE c.id = ?1", categoryId)
                .page(pageIndex, pageSize);
        return page;
    }

    public PanacheQuery<Item> listItemByProducerId(Long producerId, int pageIndex, int pageSize) {
        PanacheQuery<Item> page = find(
                "SELECT i FROM Item i INNER JOIN i.category c WHERE c.categoryName != 'Shipping' AND i.producer.id = ?1",
                producerId).page(pageIndex, pageSize);
        return page;
    }

    public PanacheQuery<Item> getAll(int pageIndex, int pageSize) {
        PanacheQuery<Item> page = find("SELECT i FROM Item i INNER JOIN i.category c WHERE c.categoryName != 'Shipping'")
                .page(pageIndex, pageSize);
        return page;
    }

    public List<Item> searchItemByName(String query) {
        return list("name LIKE ?1", "%" + query.trim() + "%");
    }

    public List<Item> getShippingMethodList() {
        return list("SELECT i FROM Item i INNER JOIN i.category c WHERE c.categoryName = 'Shipping'");
    }
}
