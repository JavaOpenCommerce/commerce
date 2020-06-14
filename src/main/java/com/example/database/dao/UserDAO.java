package com.example.database.dao;

import com.example.database.entity.Address;
import com.example.database.entity.OrderDetails;
import com.example.database.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> getById(Long id);

    List<User> getAll();

    User save(User user);

    void delete(User user);

    void deleteById(Long id);

    List<User> searchUserByEmail(String query);

    List<OrderDetails> getOrderHistoryByUserId(Long id);

    List<Address> getUserAddressListById(Long id);

    void addNewAddressToUser(Address address);
}
