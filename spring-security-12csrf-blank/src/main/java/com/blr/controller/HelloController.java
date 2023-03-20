package com.blr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("index")
    public String index() {
        return "index ok";
    }

    @RequestMapping("/withdraw")
    public String withdraw() {
        System.out.println("执行一次转账操作");
        return "执行一次转账操作";
    }
}