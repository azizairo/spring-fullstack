package com.azizairo.dao;

import com.azizairo.enteties.Customer;
import com.azizairo.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Zairov Aziz
 * @date 03.10.2023
 **/

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {

        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {

        customerRepository.save(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {

        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerWithId(Long id) {

        return customerRepository.existsById(id);
    }

    @Override
    public void deleteCustomerById(Long id) {

        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer update) {

        customerRepository.save(update);
    }
}
