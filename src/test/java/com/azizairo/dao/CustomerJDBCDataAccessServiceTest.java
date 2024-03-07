package com.azizairo.dao;

import com.azizairo.AbstractContainerBaseTest;
import com.azizairo.enteties.Customer;
import com.azizairo.util.CustomerRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractContainerBaseTest {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {

        underTest = new CustomerJDBCDataAccessService(
                getJDBCTemplate(), customerRowMapper
        );
    }

    @Test
    void shouldFindAllSuccessfully() {

        //Given
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID())
                .age(20)
                .build();

        underTest.insertCustomer(customer);

        //When
        List<Customer> customers = underTest.findAll();

        //Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void shouldFindByIdSuccessfully() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        Customer expected = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(expected);

        Long id = underTest.findAll()
                .stream()
                .filter( c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.findById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(actualCustomer -> {
            assertThat(actualCustomer.getId()).isEqualTo(id);
            assertThat(actualCustomer.getName()).isEqualTo(expected.getName());
            assertThat(actualCustomer.getEmail()).isEqualTo(expected.getEmail());
            assertThat(actualCustomer.getAge()).isEqualTo(expected.getAge());
        });
    }

    @Test
    void shouldReturnEmptyWhenSelectCustomerId() {

        // Given
        Long id = -1L;

        //When
        var actual = underTest.findById(id);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldInsertCustomerSuccessfully() {

        //Given

        //When

        //Then
    }

    @Test
    void shouldExistsCustomerWithEmail() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(customer);

        //When
        boolean actual = underTest.existsCustomerWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void shouldNotExistsCustomerWithEmail() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        boolean actual = underTest.existsCustomerWithEmail(email);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void shouldExistsCustomerWithId() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(customer);

        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existsCustomerWithId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void shouldNotExistsCustomerWithId() {

        //Given
        Long id = -1L;

        //When
        var actual = underTest.existsCustomerWithId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void shouldDeleteCustomerByIdSuccessfully() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(customer);

        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteCustomerById(id);

        //Then
        Optional<Customer> actual = underTest.findById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void shouldUpdateCustomerNameSuccessfully() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer initial = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(initial);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "Foo";

        //When
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);
        underTest.updateCustomer(update);

        //Then
        Optional<Customer> actual = underTest.findById(id);

        assertThat(actual).isPresent().hasValueSatisfying(actualCustomer -> {
            assertThat(actualCustomer.getId()).isEqualTo(id);
            assertThat(actualCustomer.getName()).isEqualTo(newName);
            assertThat(actualCustomer.getEmail()).isEqualTo(initial.getEmail());
            assertThat(actualCustomer.getAge()).isEqualTo(initial.getAge());
        });
    }

    @Test
    void shouldUpdateCustomerEmailSuccessfully() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer initial = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(initial);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setEmail(newEmail);

        underTest.updateCustomer(updatedCustomer);

        //Then
        Optional<Customer> actual = underTest.findById(id);

        assertThat(actual).isPresent().hasValueSatisfying(actualCustomer -> {
            assertThat(actualCustomer.getId()).isEqualTo(id);
            assertThat(actualCustomer.getName()).isEqualTo(initial.getName());
            assertThat(actualCustomer.getEmail()).isEqualTo(newEmail);
            assertThat(actualCustomer.getAge()).isEqualTo(initial.getAge());
        });
    }

    @Test
    void shouldUpdateCustomerAgeSuccessfully() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer initial = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(initial);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 100;

        //When
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setAge(newAge);

        underTest.updateCustomer(updatedCustomer);

        //Then
        Optional<Customer> actual = underTest.findById(id);

        assertThat(actual).isPresent().hasValueSatisfying(actualCustomer -> {
            assertThat(actualCustomer.getId()).isEqualTo(id);
            assertThat(actualCustomer.getName()).isEqualTo(initial.getName());
            assertThat(actualCustomer.getEmail()).isEqualTo(initial.getEmail());
            assertThat(actualCustomer.getAge()).isEqualTo(newAge);
        });
    }

    @Test
    void shouldUpdateAllPropertiesOfCustomer() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer initial = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(initial);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        var updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setEmail(FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID());
        updatedCustomer.setName(FAKER.name().fullName());
        updatedCustomer.setAge(100);

        underTest.updateCustomer(updatedCustomer);

        //Then
        Optional<Customer> actual = underTest.findById(id);
        assertThat(actual).isPresent().hasValue(updatedCustomer);
    }

    @Test
    void shouldNotUpdateWhenNothingToUpdate() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer initial = Customer.builder()
                .name(FAKER.name().fullName())
                .email(email)
                .age(20)
                .build();

        underTest.insertCustomer(initial);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Customer emptyUpdate = new Customer();
        emptyUpdate.setId(id);

        underTest.updateCustomer(emptyUpdate);

        //Then
        Optional<Customer> actual = underTest.findById(id);

        assertThat(actual).isPresent().hasValueSatisfying(actualCustomer -> {
           assertThat(actualCustomer.getId()).isEqualTo(id);
           assertThat(actualCustomer.getAge()).isEqualTo(initial.getAge());
           assertThat(actualCustomer.getEmail()).isEqualTo(initial.getEmail());
           assertThat(actualCustomer.getName()).isEqualTo(initial.getName());
        });
    }
}