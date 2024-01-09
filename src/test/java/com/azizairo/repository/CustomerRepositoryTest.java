package com.azizairo.repository;

import com.azizairo.AbstractContainerBaseTest;
import com.azizairo.enteties.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {

        underTest.deleteAll();
    }

    @Test
    void shouldExistCustomerByEmail() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        customer = underTest.save(customer);

        //When
        var actual = underTest.existsCustomerByEmail(customer.getEmail());

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void shouldNotExistCustomerByEmail() {

        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //When
        boolean actual = underTest.existsCustomerByEmail(email);

        //Then
        assertThat(actual).isFalse();
    }
}