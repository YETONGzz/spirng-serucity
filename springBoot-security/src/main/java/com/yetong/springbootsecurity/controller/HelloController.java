package com.yetong.springbootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());
        new Thread(() -> {
            SecurityContext context2 = SecurityContextHolder.getContext();
            Authentication authentication2 = context2.getAuthentication();
            System.out.println(" 线程" + Thread.currentThread() + "" + authentication2);
        }).start();
        return "hello";
    }


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
