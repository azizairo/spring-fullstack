package com.azizairo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zairov Aziz
 **/

@RestController
public class PingPongController {

    record PingPong(String result) {}

    @GetMapping("/ping")
    public PingPong getPingPong() {

        return new PingPong("Pong");
    }
}
