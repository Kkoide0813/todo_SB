package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class IndexController {

    // http:localhost:8080　-> "Hello, world!"
    // Get -> "Hello, world!"

    @GetMapping
    public String index(){
        return "index";
    }
}
