package com.yanle.shiro.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.yanle.shiro.mapper")
public class MybatisConfig {
}
