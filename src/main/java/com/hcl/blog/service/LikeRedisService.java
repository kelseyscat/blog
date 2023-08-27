package com.hcl.blog.service;

import com.hcl.blog.entity.Like;
import com.hcl.blog.entity.LikeCount;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface LikeRedisService {
    /**
     * 点赞。状态为1
     * @param articleId
     * @param ip
     */
    void saveLiked2Redis(int articleId, String ip);

    /**
     * 取消点赞。将状态改变为0
     * @param articleId
     * @param ip
     */
    void unlikeFromRedis(int articleId, String ip);

    /**
     * 从Redis中删除一条点赞数据
     * @param articleId
     * @param ip
     */
    void deleteLikedFromRedis(int articleId, String ip);

    /**
     * 设该文章的点赞数
     * @param articleId
     */
    void setLikedCount(int articleId, int cnt);

    /**
     * 该文章的点赞数加1
     * @param articleId
     */
    void incrementLikedCount(int articleId);

    /**
     * 该文章的点赞数减1
     * @param articleId
     */
    void decrementLikedCount(int articleId);

    /**
     * 获取文章点赞数
     * @param articleId
     */
    Integer getLikedCount(int articleId);
    /**
     * 获取ip对某文章的点赞情况
     * @param articleId
     */
    Integer getLikeDataFromRedis(int articleId, String ip);

}
