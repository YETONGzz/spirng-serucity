package com.yetong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class LoginFilterConfig {

    @Autowired
    AuthenticationManager authenticationManager;

    @Bean
    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUsernameParameter("userName");
        loginFilter.setPasswordParameter("passWord");
        loginFilter.setAuthenticationSuccessHandler(new MySuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new MyFailureHandler());
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setAuthenticationManager(authenticationManager);
        return loginFilter;
    }
}
