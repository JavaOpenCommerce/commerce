package com.example.database.dao;

import com.example.database.entity.Address;
import com.example.database.entity.OrderDetails;
import com.example.database.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    private final EntityManager em;

    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = em.find(User.class, id);
        return ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.lastName", User.class)
                .getResultList();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
            em.flush();
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public void delete(User user) {
        if (em.contains(user)) {
            em.remove(user);
        }
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<User> searchUserByEmail(String query) {
        return em.createQuery("SELECT u FROM User u WHERE u.email LIKE :query", User.class)
                .setParameter("query", "%" + query.trim() + "%")
                .getResultList();
    }

    @Override
    public List<OrderDetails> getOrderHistoryByUserId(Long id) {
        return em.createQuery("SELECT od FROM OrderDetails od WHERE od.user.id = :id", OrderDetails.class)
                .setParameter("id", id)
                .getResultList();
    }
    ;
    @Override
    public List<Address> getUserAddressListById(Long id) {
        return em.createQuery("SELECT a FROM Address a WHERE a.user.id = :id", Address.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void addNewAddressToUser(Address address) {
        em.persist(address);
    }
}
