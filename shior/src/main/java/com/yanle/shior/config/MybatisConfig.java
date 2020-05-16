package com.yanle.shior.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.howie.shiro.mapper")
public class MybatisConfig {
}
