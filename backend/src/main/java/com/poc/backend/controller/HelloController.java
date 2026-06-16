package com.poc.backend.controller;
import com.poc.backend.dto.HelloResponse;
import com.poc.backend.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/api/hello")
    public HelloResponse sayHello() {
        return helloService.getHelloMessage();
    }

}