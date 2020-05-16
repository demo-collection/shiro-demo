package com.yanle.shiro.controller;

import com.yanle.shiro.model.ResultMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {
    private final ResultMap resultMap;

    public GuestController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @GetMapping("/enter")
    public ResultMap login() {
        return resultMap.success().message("欢迎进入， 你的身份是游客");
    }

    @GetMapping("/getmessage")
    public ResultMap submitLogin() {
        return resultMap.success().message("您拥有获得该接口的信息的权限!");
    }
}
