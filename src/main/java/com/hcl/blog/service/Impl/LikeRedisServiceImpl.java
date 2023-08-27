package com.hcl.blog.service.Impl;

import com.hcl.blog.entity.Like;
import com.hcl.blog.entity.LikeCount;
import com.hcl.blog.service.LikeRedisService;
import com.hcl.blog.util.RedisKeyUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LikeRedisServiceImpl implements LikeRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveLiked2Redis(int articleId, String ip) {
        String key = RedisKeyUtil.getLikedKey(articleId, ip);
        redisTemplate.opsForHash().put(RedisKeyUtil.MAP_KEY_USER_LIKED, key, 1); // 点赞状态值为1
    }

    @Override
    public void unlikeFromRedis(int articleId, String ip) {
        String key = RedisKeyUtil.getLikedKey(articleId, ip);
        redisTemplate.opsForHash().put(RedisKeyUtil.MAP_KEY_USER_LIKED, key, -1); // 取消点赞状态值为-1
    }

    @Override
    public void deleteLikedFromRedis(int articleId, String ip) {
        String key = RedisKeyUtil.getLikedKey(articleId, ip);
        redisTemplate.opsForHash().delete(RedisKeyUtil.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void setLikedCount(int articleId, int cnt) {
        redisTemplate.opsForHash().put(RedisKeyUtil.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId), cnt);
    }

    @Override
    public void incrementLikedCount(int articleId) {
        redisTemplate.opsForHash().increment(RedisKeyUtil.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId), 1);
    }

    @Override
    public void decrementLikedCount(int articleId) {
        redisTemplate.opsForHash().increment(RedisKeyUtil.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId), -1);
    }

    @Override
    public Integer getLikedCount(int articleId) {
        return (Integer) redisTemplate.opsForHash().get(RedisKeyUtil.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId));
    }

    @Override
    public Integer getLikeDataFromRedis(int articleId, String ip) {
        String key = RedisKeyUtil.getLikedKey(articleId, ip);
        return (Integer) redisTemplate.opsForHash().get(RedisKeyUtil.MAP_KEY_USER_LIKED, key);
    }

}
