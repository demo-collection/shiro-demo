package com.yanle.shiro.jwt.controller;

import com.yanle.shiro.jwt.mapper.UserMapper;
import com.yanle.shiro.jwt.model.ResultMap;
import com.yanle.shiro.jwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResultMap resultMap;

    public ResultMap login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        String realPassword = userMapper.getPassword(username);
        if (realPassword == null) {
            return resultMap.fail().code(401).message("用户名错误");
        } else if (!realPassword.equals(password)) {
            return resultMap.fail().code(401).message("密码错误");
        } else {
            return  resultMap.success().code(200).message(JWTUtil.createToken(username));
        }
    }

    @RequestMapping("/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return resultMap.success().code(401).message(message);
    }
}
