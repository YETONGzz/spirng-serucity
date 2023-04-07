package com.yetong.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yetong.config.JWTConfig;
import com.yetong.entity.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
public class JWTTokenUtil {

    /**
     * 创建Token
     *
     * @param user 用户信息
     * @return
     */
    public static String createAccessToken(LoginUser user) {
        String token = Jwts.builder().setId(// 设置JWT
                        user.getId().toString()) // 用户Id
                .setSubject(user.getUsername()) // 主题
                .setIssuedAt(new Date()) // 签发时间
                .setIssuer("C3Stones") // 签发者
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration)) // 过期时间
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret) // 签名算法、密钥
                .claim("authorities", JSON.toJSONString(user.getAuthorities())).compact(); // 自定义其他属性，如用户组织机构ID，用户所拥有的角色，用户权限信息等
        return JWTConfig.tokenPrefix + token;
    }

    /**
     * 解析Token
     *
     * @param token Token信息
     * @return
     */
    public static LoginUser parseAccessToken(HttpServletRequest request) {
        String token = request.getHeader(JWTConfig.tokenHeader);
        LoginUser sysUserDetails = null;
        if (StringUtils.hasText(token)) {
            try {
                // 去除JWT前缀
                token = token.substring(JWTConfig.tokenPrefix.length());

                // 解析Token
                Claims claims = Jwts.parser().setSigningKey(JWTConfig.secret).parseClaimsJws(token).getBody();

                // 获取用户信息
                sysUserDetails = new LoginUser();
                sysUserDetails.setId(Integer.valueOf(claims.getId()));
                sysUserDetails.setUsername(claims.getSubject());
                // 获取角色
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                String authority = claims.get("authorities").toString();
                if (StringUtils.hasText(authority)) {
                    List<Map<String, String>> authorityList = JSON.parseObject(authority,
                            new TypeReference<List<Map<String, String>>>() {
                            });
                    for (Map<String, String> role : authorityList) {
                        if (!role.isEmpty()) {
                            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                        }
                    }
                }
                sysUserDetails.setAuthorities(authorities);
            } catch (Exception e) {
                log.error("解析Token异常：" + e);
            }
        }
        return sysUserDetails;
    }
}
