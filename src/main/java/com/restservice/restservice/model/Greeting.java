package com.restservice.restservice.model;

import lombok.Data;

@Data
public class Greeting{
    private final long id;
    private final String content;
    private final String timestamp;
}