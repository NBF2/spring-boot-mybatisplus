package com.cloud.spring.demo.config.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CorsUserAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            return super.onAccessDenied(request, response);
        } else {
            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return super.onPreHandle(request, response, mappedValue);
    }
}
