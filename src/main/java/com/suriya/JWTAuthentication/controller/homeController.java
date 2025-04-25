package com.suriya.JWTAuthentication.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @PostMapping("/home/{username}")
    public String home(@PathVariable String username){
        return "Welcome back, "+username;
    }
}
