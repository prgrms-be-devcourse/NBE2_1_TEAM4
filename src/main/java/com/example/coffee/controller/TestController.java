package com.example.coffee.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping("/hello")
    public String hello() {


        return "/sample";
    }

    @GetMapping("/test")
    public String test(){

        return "/index";
    }
}
