package com.restservice.restservice;

import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
// Tells Spring that this code describes an available endpoint
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // Tell Spring to use greeting method to answer requests sent to localhost/greeting
    @GetMapping("/greeting")
    // ReqParam tells Spring it expects param name and uses default value if its not there
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping({"/greeting/{name}","/greeting/"})
    // PathVar extracts templated part of URI
    public Greeting greetingGet(@PathVariable(value = "name", required = false) String name) {
        log.info("Greeting request received {}", name);
        String username = name == null ? "World" : name;
        return new Greeting(counter.incrementAndGet(), String.format(template, username));
    }

    @PostMapping("/greeting")
    public Greeting greetingPost(@RequestBody String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}