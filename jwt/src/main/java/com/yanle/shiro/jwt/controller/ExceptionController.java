package com.yanle.shiro.jwt.controller;

import com.yanle.shiro.jwt.model.ResultMap;
import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装异常处理
 */
@RestControllerAdvice
public class ExceptionController {
    @Autowired
    private ResultMap resultMap;

    // 不活 shiro 异常
    @ExceptionHandler(ShiroException.class)
    public ResultMap handle401() {
        return resultMap.fail().code(401).message("没有权限访问");
    }

    // 捕获其他异常
    public ResultMap globalException(HttpServletRequest request, Throwable ex) {
        return resultMap.fail()
                .code(handleGetState(request).value())
                .message("访问出错， 无法访问： " + ex.getMessage());
    }


    private HttpStatus handleGetState(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
