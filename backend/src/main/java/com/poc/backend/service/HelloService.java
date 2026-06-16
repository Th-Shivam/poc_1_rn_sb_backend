package com.poc.backend.service;

import com.poc.backend.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public HelloResponse getHelloMessage() {
        return new HelloResponse(
                "Hello from Spring Boot 🚀",
                "success"
        );
    }
}