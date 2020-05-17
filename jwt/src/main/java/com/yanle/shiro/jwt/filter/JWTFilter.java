package com.yanle.shiro.jwt.filter;

import com.yanle.shiro.jwt.shiro.JWTToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    /**
     * 如果带有 token，则对 token 进行检查，否则直接通过
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                // token 错误
                responseError(response, e.getMessage());
            }
        }
        return true;
    }

    /**
     * 执行登陆操作
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Token");
        JWTToken jwtToken = new JWTToken(token);

        // 提交给realm进行登录
        getSubject(request, response).login(jwtToken);

        // 如果没有抛出错误， 说明登录成功
        return true;
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized" + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 判断用户是否想要等录入
     * 检测 header 里面包含是否是 token 字段
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("Token");
        return token != null;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) request;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
