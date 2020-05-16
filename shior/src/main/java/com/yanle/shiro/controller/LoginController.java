package com.yanle.shiro.controller;

import com.yanle.shiro.mapper.UserMapper;
import com.yanle.shiro.model.ResultMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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

    @PostMapping("/login")
    public ResultMap login(@RequestBody Map<String, String> map) {

        logger.info("用户名： {}, 密码： {}", map.get("username"), map.get("password"));

        // 创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 生成令牌
        UsernamePasswordToken token = new UsernamePasswordToken(map.get("username"), map.get("password"));
        // 认证
        subject.login(token);

        // 根据权限返回指定数据
        String role = userMapper.getRole(map.get("username"));
        if (role.equals("user")) {
            return resultMap.success().message("欢迎登陆");
        } else if (role.equals("admin")){
            return resultMap.success().message("欢迎来到管理员页面");
        }

        // todo 根据权限返回数据
        return resultMap.fail().message("权限错误！");
    }
}
