package com.yetong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.RememberMeServices;

@Configuration
public class LoginFilterConfig {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RememberMeServices rememberMeServices;

    @Bean
    public LoginFilter loginFilter() {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setAuthenticationSuccessHandler(new MySuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new MyFailureHandler());
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setRememberMeServices(rememberMeServices);
        return loginFilter;
    }
}
