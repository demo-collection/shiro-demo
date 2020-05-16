package com.yanle.shiro.controller;

import com.yanle.shiro.mapper.UserMapper;
import com.yanle.shiro.model.ResultMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final ResultMap resultMap;

    @Autowired
    private UserMapper userMapper;

    public LoginController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @GetMapping("/not-login")
    public ResultMap notLogin() {
        return resultMap.success().message("您没有登录");
    }

    @GetMapping("/not-role")
    public ResultMap notRole() {
        return resultMap.success().message("没有权限");
    }

    @GetMapping("/logout")
    public ResultMap logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return resultMap.success().message("成功注销");
    }

    public ResultMap login(String username, String password) {
        // 创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 生成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 认证
        subject.login(token);

        // todo 根据权限返回数据
        return resultMap.fail().message("权限错误！");
    }
}
