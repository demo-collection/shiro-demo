package com.yanle.shiro.jwt.controller;

import com.yanle.shiro.jwt.mapper.UserMapper;
import com.yanle.shiro.jwt.model.ResultMap;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResultMap resultMap;

    @GetMapping("/get_user")
    @RequiresRoles("admin")
    public ResultMap getUser() {
        List<String> list = userMapper.getUser();
        return resultMap.success().code(200).message(list);
    }


    @PostMapping("/ban_user")
    public ResultMap banUser(String username) {
        userMapper.banUser(username);
        return resultMap.success().code(200).message("封号成功");
    }
}
