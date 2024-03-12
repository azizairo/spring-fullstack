package com.azizairo.services;

import com.azizairo.dao.CustomerDao;
import com.azizairo.dto.CustomerRegistrationRequest;
import com.azizairo.dto.CustomerUpdateRequest;
import com.azizairo.exceptions.RequestValidationException;
import com.azizairo.exceptions.DuplicateResourceException;
import com.azizairo.exceptions.ResourceNotFoundException;
import com.azizairo.enteties.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zairov Aziz
 * @date 30.09.2023
 **/

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {

        return customerDao.findAll();
    }

    public Customer getCustomer(Long id) {

        return customerDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id {%s} not found!".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        String email = customerRegistrationRequest.email();
        if (customerDao.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException(  "Customer already exists!");
        } else {
            customerDao.insertCustomer(
                    Customer.builder()
                            .name(customerRegistrationRequest.name())
                            .email(customerRegistrationRequest.email())
                            .age(customerRegistrationRequest.age())
                            .build()
            );
        }
    }

    public void deleteCustomerById(Long id) {

        if (!customerDao.existsCustomerWithId(id)) {
            throw new ResourceNotFoundException(
                    "Customer with id {%s} not found!".formatted(id)
            );
        }

        customerDao.deleteCustomerById(id);
    }

    public void updateCustomerById(Long customerId, CustomerUpdateRequest customerUpdateRequest) {

        Customer customer = getCustomer(customerId);

        boolean changes = false;
        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException("Customer with given email already exists!");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
