package com.yetong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.yetong")
//@EnableTransactionManagement
@MapperScan("com.yetong.dao")
public class SpringSecurityRedisJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRedisJwtApplication.class, args);
    }


}
