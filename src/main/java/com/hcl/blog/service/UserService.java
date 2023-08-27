package com.hcl.blog.service;

import com.hcl.blog.entity.LoginTicket;
import com.hcl.blog.entity.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> login(String username, String password, int expiredSeconds);

    void logout(String ticket);

    LoginTicket findLoginTicket(String ticket);

    User findUserById(int id);

}
