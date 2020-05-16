package com.yanle.shiro.controller;

import com.yanle.shiro.model.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final ResultMap resultMap;

    public UserController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @GetMapping("/getmessage")
    public ResultMap getMessage() {
        return resultMap.success().message("您拥有用户权限，可以获得该接口的信息！");
    }
}
