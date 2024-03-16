package com.azizairo;

import com.azizairo.enteties.Customer;
import com.azizairo.repository.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Random;

/**
 * @author Zairov Aziz
 * @date 26.09.2023
 **/

@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    private static void printBeans(ConfigurableApplicationContext ctx) {

        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName: beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {

        return args -> {
            var faker = new Faker();
            var name = faker.name();
            var firstName = name.firstName();
            var lastName = name.lastName();

            Customer customer = Customer.builder()
                    .name(firstName)
                    .email(firstName.toLowerCase(Locale.ROOT) + "." + lastName.toLowerCase(Locale.ROOT) + "@gmail.com")
                    .age(new Random().nextInt(16, 99))
                    .build();
            customerRepository.save(customer);
        };
    }

    @Bean("foo")
    public Foo beanFoo() {

        return new Foo("bar");
    }

    record Foo(String name) {}

}
