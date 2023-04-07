package com.yetong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello2")
    public void hello2(){
        System.out.println("hello");
    }

    @RequestMapping("/admin")
    public void admin(){
        System.out.println("admim");
    }

    @RequestMapping("/employee")
    public void employee(){
        System.out.println("employee");
    }
}
