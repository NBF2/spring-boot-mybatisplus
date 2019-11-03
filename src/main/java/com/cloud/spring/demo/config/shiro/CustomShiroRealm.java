package com.cloud.spring.demo.config.shiro;

import com.cloud.spring.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles= getRolesByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles= new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();

        String password = userService.selectPasswordByUserName(userName);
        if (null == password) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, password, getName());
        return authenticationInfo;
    }
}
