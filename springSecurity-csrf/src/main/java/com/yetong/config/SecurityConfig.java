package com.yetong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

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
        http.authorizeRequests()
                .mvcMatchers("/index").permitAll()
                .anyRequest()
                .authenticated()
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
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .tokenRepository(persistentTokenRepository())//指定token存储位置
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("尚未认证，请进行认证操作！");
                })
                .accessDeniedHandler((request, response, e) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("无权访问!");
                })
                .and()
                // at: 用某个 filter 替换过滤器链中哪个 filter
                // before: 放在过滤器链中哪个 filter 之前
                // after: 放在过滤器链中那个 filter 之后
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/doLogin");
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
