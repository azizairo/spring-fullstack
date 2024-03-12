package com.azizairo.util;

import com.azizairo.enteties.Customer;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void shouldMapRow() throws SQLException {

        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet mockedResultSet = mock(ResultSet.class);
        when(mockedResultSet.getLong("id")).thenReturn(1L);
        when(mockedResultSet.getInt("age")).thenReturn(19);
        when(mockedResultSet.getString("name")).thenReturn("Jamila");
        when(mockedResultSet.getString("email")).thenReturn("jamila@gmail.com");

        //When
        Customer actual = customerRowMapper.mapRow(mockedResultSet, 1);

        //Then
        Customer expected = new Customer(
                1L,
                "Jamila",
                "jamila@gmail.com",
                19

        );
        assertThat(actual).isEqualTo(expected);
    }
}