package com.yanle.shiro.jwt.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.yanle.shiro.jwt.mapper")
public class MybatisConfig {
}
