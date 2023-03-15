package com.yetong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    @Lazy
    LoginFilter loginFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/index").permitAll()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("logoutA", "GET"),
                        new AntPathRequestMatcher("logoutB", "GET")))
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .invalidateHttpSession(true)//注销成功后清除session
                .clearAuthentication(true)//清除认证信息
                .and()
                // at: 用某个 filter 替换过滤器链中哪个 filter
                // before: 放在过滤器链中哪个 filter 之前
                // after: 放在过滤器链中那个 filter 之后
                .csrf()
                .disable();
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
