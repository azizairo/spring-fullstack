package com.azizairo.util;

import com.azizairo.enteties.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Zairov Aziz
 * @date 23.10.2023
 **/

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_EMAIL = "email";
    private static final String COLUMN_NAME_AGE = "age";


    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Customer(
                rs.getLong(COLUMN_NAME_ID),
                rs.getString(COLUMN_NAME_NAME),
                rs.getString(COLUMN_NAME_EMAIL),
                rs.getInt(COLUMN_NAME_AGE)
        );
    }
}
