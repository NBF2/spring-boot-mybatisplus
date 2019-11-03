package com.cloud.spring.demo.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        /*Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authcFilter", new CorsUserAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);*/

        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        map.put("/api/v1/users/addUser", "anon");
        //对所有用户认证
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //创建securityManager
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customShiroRealm());
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomShiroRealm customShiroRealm() {
        CustomShiroRealm customShiroRealm = new CustomShiroRealm();
        customShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return customShiroRealm;
    }

    //设置加密算法
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        return credentialsMatcher;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie cookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(20000);
        return cookie;
    }
}
