package com.yetong.service;

import com.yetong.entity.LoginUser;
import org.springframework.security.core.token.Token;

import javax.servlet.http.HttpServletRequest;

/**
 * Token管理器
 * 可存储到redis
 * 基于redis
 */
public interface TokenService {

    Token saveToken(LoginUser loginUser);

    void refresh(LoginUser loginUser);

    LoginUser getLoginUser(HttpServletRequest request);

    void delLoginUser(String token);

    void verifyToken(LoginUser loginUser);

}

