package com.example.database.dao;

import com.example.database.entity.Address;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class AddressDAOImpl implements AddressDAO {


    private final EntityManager em;

    public AddressDAOImpl(EntityManager em) {this.em = em;}


    @Override
    public Optional<Address> getById(Long id) {
        Address address = em.find(Address.class, id);
        return ofNullable(address);
    }

    @Override
    public List<Address> getAll() {
        return em.createQuery("SELECT a FROM Address a", Address.class)
                .getResultList();
    }

    @Override
    public List<Address> getAddressByZip(String zip) {
        return em.createQuery("SELECT a FROM Address a WHERE a.zip = :zip")
                .setParameter("zip", zip)
                .getResultList();
    }

    @Override
    public List<Address> getAddressByCity(String city) {
        return em.createQuery("SELECT a FROM Address a WHERE a.city = :city")
                .setParameter("city", city)
                .getResultList();
    }
}
