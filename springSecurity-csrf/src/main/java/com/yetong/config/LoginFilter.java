package com.yetong.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义前后端分离 登录过滤器
 */

public  class LoginFilter extends UsernamePasswordAuthenticationFilter {


    /**
     * 重写认证方法
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                Map<String, String> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER,map.get(AbstractRememberMeServices.DEFAULT_PARAMETER));
                String userName = map.get("username");
                String passWord = map.get("password");
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, passWord);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.attemptAuthentication(request, response);
    }

//    public static int a = 0;
//
//    static ReentrantLock reentrantLock = new ReentrantLock();
//
//    public static void main(String[] args) {
//        LoginFilter loginFilter = new LoginFilter();
//        for (int i = 0; i < 100; i++) {
//            new Thread(() -> {
//                try {
//                    loginFilter.test2();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }).start();
//        }
//
//
//    }
//
//    public static void test() throws InterruptedException {
//        reentrantLock.lock();
//        try {
//            if (a < 10) {
//                TimeUnit.SECONDS.sleep(2);
//                a++;
//                System.out.println(a);
//
//            }
//        }finally {
//            System.out.println("hhhhh");
//            reentrantLock.unlock();
//        }
//
//        System.out.println(Thread.currentThread() + "  dasdsad");
//
//    }
//
//    public  void test2() throws InterruptedException {
//        synchronized (this){
//            if (a < 10) {
//                TimeUnit.SECONDS.sleep(1);
//                a++;
//                System.out.println(a);
//            }
//        }
//
//
//        System.out.println(Thread.currentThread() + "  dasdsad");
//
//    }

}
