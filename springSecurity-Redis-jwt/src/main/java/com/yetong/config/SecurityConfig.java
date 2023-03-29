package com.yetong.config;

import com.yetong.config.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    @Lazy
    LoginFilter loginFilter;

    @Autowired
    RememberMeServices rememberMeServices;

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //暴露
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);//启动创建表结构
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                //基于url http请求的权限管理
//                .mvcMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .mvcMatchers("/employee")
//                .hasAnyRole("ADMIN", "EMPLOYEE")
//                .mvcMatchers("/index").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new OrRequestMatcher(
//                        new AntPathRequestMatcher("logoutA", "GET"),
//                        new AntPathRequestMatcher("logoutB", "GET")))
//                .logoutSuccessHandler(new MyLogoutSuccessHandler())
//                .invalidateHttpSession(true)//注销成功后清除session
//                .clearAuthentication(true)//清除认证信息
//                .and()
//                .rememberMe()
//                .rememberMeServices(rememberMeServices)
//                .tokenRepository(persistentTokenRepository())//指定token存储位置
//                .and()
//                // at: 用某个 filter 替换过滤器链中哪个 filter
//                // before: 放在过滤器链中哪个 filter 之前
//                // after: 放在过滤器链中那个 filter 之后
//                .csrf()
//                .disable();
//        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
