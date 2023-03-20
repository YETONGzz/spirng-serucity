package com.yetong.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {

    @PostMapping("/hello")
    public void hello(){
        System.out.println("hello");
    }
}
