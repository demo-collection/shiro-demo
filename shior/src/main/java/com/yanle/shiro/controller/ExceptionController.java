package com.yanle.shiro.controller;


import com.yanle.shiro.model.ResultMap;
import org.apache.shiro.authc.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController extends Throwable {
    private final ResultMap resultMap;

    @Autowired
    public ExceptionController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @ExceptionHandler(AccountException.class)
    public ResultMap handleShiroException(Exception e) {
        return resultMap.fail().message(e.getMessage());
    }
}
