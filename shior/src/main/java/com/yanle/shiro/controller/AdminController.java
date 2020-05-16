package com.yanle.shiro.controller;

import com.yanle.shiro.model.ResultMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ResultMap resultMap;


    public AdminController(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    @RequestMapping(value = "/getmessage", method = RequestMethod.GET)
    public ResultMap getMessage() {
        return resultMap.success().message("拥有管理员权限，可以获取该接口信息！");
    }
}
