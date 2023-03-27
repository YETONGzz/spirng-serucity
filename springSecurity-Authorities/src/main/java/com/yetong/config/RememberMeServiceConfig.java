package com.yetong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class RememberMeServiceConfig {

    @Autowired
    @Lazy
    PersistentTokenRepository jdbcTokenRepository;

    @Bean
    public MyRememberMeService myRememberMeService() {
        return new MyRememberMeService("yetong", new MyUserDetailsService(), jdbcTokenRepository);
    }
}
