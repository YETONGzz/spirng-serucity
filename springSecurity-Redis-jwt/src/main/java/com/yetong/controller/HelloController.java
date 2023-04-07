package com.yetong.controller;

import com.yetong.entity.LoginUser;
import com.yetong.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    RedisService redisService;
    @RequestMapping("/hello")
    public void hello(){
        System.out.println("hello");
        redisService.set("user",new LoginUser());
        System.out.println(redisService.get("user"));
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
