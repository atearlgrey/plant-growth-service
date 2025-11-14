package com.idc.plantgrowth.interfaces.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public String users() {
        return "Hello World";
    }

    @GetMapping("/public")
    public String publicUser() {
        return "Hello Public User";
    }
}
