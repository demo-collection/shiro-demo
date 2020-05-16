package com.yanle.shiro.controller;


import com.yanle.shiro.model.ResultMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class ExceptionController {
    private final ResultMap resultMap;

    public ExceptionController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @ExceptionHandler(AccountException.class)
    public ResultMap handleShiroException(Exception e) {
        return resultMap.fail().message(e.getMessage());
    }
}
