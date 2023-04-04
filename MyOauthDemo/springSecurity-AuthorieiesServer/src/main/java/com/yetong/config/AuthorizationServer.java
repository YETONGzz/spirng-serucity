//package com.yetong.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//
///**
// * 自定义授权服务器配置
// */
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//
//    //授权码这种模式:
//    // 1.请求用户是否授权 /oauth/authorize
//    // 完整路径: http://localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
//    // 2.授权之后根据获取的授权码获取令牌 /oauth/token  id secret redirectUri  授权类型: authorization_code
//    // 完整路径: curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=authorization_code&code=IwvCtx&redirect_uri=http://www.baidu.com' "http://client:secret@localhost:8080/oauth/token"
//    // 3.支持令牌刷新  /oauth/token  id  secret  授权类型 : refresh_token  刷新的令牌: refresh_token
//    // 完整路径: curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=refresh_token&refresh_token=f6583d8a-598c-46bb-81d8-01fa6484cf05&client_id=client' "http://client:secret@localhost:8080/oauth/token"
//
//    /**
//     * 配置客户端细节 如 客户端 id 秘钥 重定向 url 等
//     *
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("client")//支持授权的客户端id
//                .secret(passwordEncoder.encode("secret"))//秘钥
//                .redirectUris("http://www.baidu.com")//重定向uri
//                .scopes("client:read,user:read")//授权后的权限
//                //授权模式
//                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials");
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.userDetailsService(userDetailsService);//开启刷新令牌必须指定
//        endpoints.authenticationManager(authenticationManager);
//    }
//}
