package com.yetong.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;


//    @Bean
//    public UserDetailsService config(){
//        InMemoryUserDetailsManager memoryUserDetails = new InMemoryUserDetailsManager();
//        memoryUserDetails.createUser(User.withUsername("root").password("{noop}root").roles("admin").build());
//        return memoryUserDetails;
//    }


    //springBoot  在security的工厂中的默认的AuthenticationManger 不推荐这种做法
//    @Autowired
//    public void init(AuthenticationManagerBuilder builder){
//        System.out.println(builder);
//    }


    //自定义AuthenticationManger 一旦重写了configure方法 那么自定义的认证管理器会覆盖掉SpringBoot中默认的认证管理器
    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(myUserDetailsService);
    }

    //想要AuthenticationManger在 其他的bean中使用 需要将springBoot工厂中本地的AuthenticationManger暴露出去
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //【注意事项】放行资源要放在前面，认证的放在后面
        http.authorizeRequests()
                .mvcMatchers("/index").permitAll() //代表放行index的所有请求
                .mvcMatchers("/loginHtml").permitAll() //放行loginHtml请求
                .anyRequest().authenticated()//代表其他请求需要认证
                .and()
                .formLogin()//表示其他需要认证的请求通过表单认证
                //loginPage 一旦你自定义了这个登录页面，那你必须要明确告诉SpringSecurity日后哪个url处理你的登录请求
                .loginPage("/loginHtml").permitAll()//用来指定自定义登录界面，不使用SpringSecurity默认登录界面  注意：一旦自定义登录页面，必须指定登录url
                //loginProcessingUrl  这个doLogin请求本身是没有的，因为我们只需要明确告诉SpringSecurity，日后只要前端发起的是一个doLogin这样的请求，
                //那SpringSecurity应该把你username和password给捕获到
                .loginProcessingUrl("/doLogin")//指定处理登录的请求url
                //.successForwardUrl("/index")//认证成功 forward 跳转路径
                .successHandler(new MyAuthenticationSuccessHandler())//登录成功给前端返回自定义数据
                .failureHandler(new MyAuthenticationFailureHandler())//认证失败给前端自定义数据
                .and()
                .logout()
                //自定义配置多种退出方式
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logoutA","GET"),
                        new AntPathRequestMatcher("/logoutB","GET")))
//                .logoutUrl("/logout")//注销登录的请求路径
                //.logoutSuccessUrl("/loginHtml")//注销成功之后的跳转页面
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .invalidateHttpSession(true)//注销成功后清除session
                .clearAuthentication(true)//清除认证信息
                .and()
                .csrf().disable(); //禁止csrf 跨站请求保护
    }


}
