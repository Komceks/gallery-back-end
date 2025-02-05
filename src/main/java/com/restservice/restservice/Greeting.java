package com.restservice.restservice;

import lombok.Data;

@Data
public class Greeting{
    private final long id;
    private final String content;
}
//public record Greeting(long id, String content) { }