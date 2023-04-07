package com.yetong.config;

import com.yetong.entity.LoginUser;
import com.yetong.util.JWTTokenUtil;
import com.yetong.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆成功后
 */
public class MySuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser sysUserDetails = (LoginUser) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(sysUserDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        ResponseUtil.responseJson(response, ResponseUtil.response(200, "登录成功", tokenMap));
    }
}
