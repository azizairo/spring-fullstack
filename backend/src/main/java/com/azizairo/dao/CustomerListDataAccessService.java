package com.azizairo.dao;

import com.azizairo.enteties.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Zairov Aziz
 * @date 30.09.2023
 **/

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private final static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1L,
                "Alex",
                "alex@gmaijl.com",
                21
        );
        customers.add(alex);

        Customer jamila = new Customer(
                2L,
                "Jamila",
                "jamila@gmaijl.com",
                19
        );
        customers.add(jamila);
    }

    @Override
    public List<Customer> findAll() {

        return customers;
    }

    @Override
    public Optional<Customer> findById(Long id) {

        return customers
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {

        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {

        return customers
                .stream()
                .anyMatch( c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Long id) {

        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long id) {

        customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {

        customers.add(update);
    }
}
