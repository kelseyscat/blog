package com.hcl.blog.service.Impl;

import com.hcl.blog.dao.UserDao;
import com.hcl.blog.entity.LoginTicket;
import com.hcl.blog.entity.User;
import com.hcl.blog.service.UserService;
import com.hcl.blog.util.RedisKeyUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<String, Object> login(String username, String password, int expiredSeconds){
        Map<String, Object> map = new HashMap<>();

        if(StringUtils.isBlank(username)){
            map.put("usernameMsg", "账号不能为空！");
        }

        if(StringUtils.isBlank(password)){
            map.put("passwordMsg", "密码不能为空！");
        }

        User user = userDao.findByUser(username);

        if(!user.getPassword().equals(password)){
            map.put("passwordMsg", "密码不正确！");
            return map;
        }

        String ticketUid = UUID.randomUUID().toString().replaceAll("-", "");

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket(ticketUid);
        loginTicket.setStatus(0); // 登录
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));  // 3600 * 12是秒

        String redisKey = RedisKeyUtil.getTicketKey(ticketUid);
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    public void logout(String ticket){
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }

    public LoginTicket findLoginTicket(String ticket){
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    public User findUserById(int id){
        return userDao.findByUserId(id);
    }
}
