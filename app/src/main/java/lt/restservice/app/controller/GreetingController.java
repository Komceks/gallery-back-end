package lt.restservice.app.controller;

import java.util.concurrent.atomic.AtomicLong;

import lt.restservice.model.Greeting;

import org.springframework.web.bind.annotation.*;

// Tells Spring that this code describes an available endpoint
@RestController
@RequestMapping("greeting")
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";
    private static final String TIMESTAMP = String.valueOf(System.currentTimeMillis());

    private final AtomicLong counter = new AtomicLong();

    // Tell Spring to use greeting method to answer requests sent to localhost/greeting
    @GetMapping
    // ReqParam tells Spring it expects param name and uses default value if its not there
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name), TIMESTAMP);
    }
}