package com.azizairo;

import org.springframework.stereotype.Service;

/**
 * @author Zairov Aziz
 * @date 02.10.2023
 **/

@Service
public class FooService {

    public Main.Foo foo;

    public FooService(Main.Foo foo) {
        this.foo = foo;
        System.out.println();
    }

    public String getFooName() {

        return foo.name();
    }

}
