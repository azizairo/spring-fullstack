package com.azizairo.repository;

import com.azizairo.enteties.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zairov Aziz
 * @date 03.10.2023
 **/

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);
}
