package com.azizairo.dto;

/**
 * @author Zairov Aziz
 * @date 17.10.2023
 **/

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {}
