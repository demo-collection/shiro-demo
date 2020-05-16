package com.yanle.shiro.mapper;

public interface UserMapper {

    String getPassword(String username);

    String getRole(String username);
}
