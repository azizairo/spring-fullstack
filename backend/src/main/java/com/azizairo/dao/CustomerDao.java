package com.azizairo.dao;

import com.azizairo.enteties.Customer;

import java.util.List;
import java.util.Optional;

/**
 * @author Zairov Aziz
 * @date 30.09.2023
 **/

public interface CustomerDao {

    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void insertCustomer(Customer customer);
    boolean existsCustomerWithEmail(String email);
    boolean existsCustomerWithId(Long id);
    void deleteCustomerById(Long id);
    void updateCustomer(Customer update);

}
