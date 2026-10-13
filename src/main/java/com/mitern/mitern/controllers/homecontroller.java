package com.mitern.mitern.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homecontroller {

    @GetMapping("/")
    public String home() {
        return "Welcome to the miTern backend!";
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "miTern API is working!";
    }
}
