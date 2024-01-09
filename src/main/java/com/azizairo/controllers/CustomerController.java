package com.azizairo.controllers;

import com.azizairo.dto.CustomerRegistrationRequest;
import com.azizairo.dto.CustomerUpdateRequest;
import com.azizairo.enteties.Customer;
import com.azizairo.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zairov Aziz
 * @date 30.09.2023
 **/

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Customer> getCustomers() {

        return customerService.getAllCustomers();
    }

    @RequestMapping(
            path = "{customerId}",
            method = RequestMethod.GET
    )
    public Customer getCustomer(@PathVariable("customerId") Long customerId) {

        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {

        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {

        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId, @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        customerService.updateCustomerById(customerId, customerUpdateRequest);
    }
}
