package com.azizairo.dao;

import com.azizairo.enteties.Customer;
import com.azizairo.util.CustomerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Zairov Aziz
 * @date 23.10.2023
 **/

@RequiredArgsConstructor
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    @Override
    public List<Customer> findAll() {

        var sql = """
                SELECT id, name, email, age FROM customer;
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(Long id) {

        var sql = """
                SELECT id, name, email, age FROM customer WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {

        var sql = """
               INSERT INTO customer(name, email, age)
               VALUES (?, ?, ?);
               """;

        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?;
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerWithId(Long id) {

        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?;
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Long id) {

        var sql = """
                DELETE
                FROM customer
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateCustomer(Customer update) {

        /*var sql = """
                UPDATE customer
                SET name = COALESCE(?, name),
                    age = COALESCE(?, age),
                    email = COALESCE(?, email)
                WHERE id = ?;
                """;
        jdbcTemplate.update(
                sql,
                update.getName(),
                update.getAge(),
                update.getEmail(),
                update.getId()
        );*/
        if (update.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?;";
            jdbcTemplate.update(sql, update.getName(), update.getId());
        }
        if (update.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?;";
            jdbcTemplate.update(sql, update.getAge(), update.getId());
        }
        if (update.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?;";
            jdbcTemplate.update(sql, update.getEmail(), update.getId());
        }
    }
}
