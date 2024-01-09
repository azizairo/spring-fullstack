package com.azizairo.dao;

import com.azizairo.enteties.Customer;
import com.azizairo.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {

        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {

        autoCloseable.close();
    }

    @Test
    void findAll() {

        //When
        underTest.findAll();

        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void findById() {

        //Given
        Long id = 1L;

        //When
        underTest.findById(id);

        //Then
        verify(customerRepository)
                .findById(id);
    }

    @Test
    void insertCustomer() {

        //Given
        Customer customer = new Customer(
                1L, "Ali", "ali@gmail.com", 2
        );

        //When
        underTest.insertCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerWithEmail() {

        //Given
        String email = "foo@gmail";

        //When
        underTest.existsCustomerWithEmail(email);

        //Then
        verify(customerRepository)
                .existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {

        //Given
        Long id = 1L;

        //When
        underTest.existsCustomerWithId(id);

        //Then
        verify(customerRepository)
                .existsById(id);
    }

    @Test
    void deleteCustomerById() {

        //Given
        Long id = 1L;

        //When
        underTest.deleteCustomerById(id);

        //Then
        verify(customerRepository)
                .deleteById(id);
    }

    @Test
    void updateCustomer() {

        //Given
        Customer customer = new Customer(
                1L, "Ali", "ali@gmail.com", 2
        );

        //When
        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer);
    }
}