package com.hcl.blog.service;

import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.Like;

import java.util.List;

public interface LikeService {

    int getLikeNum(int articleId);

    List<Like> getLikeInfoByArticleId(int articleId);

    /**
     * 保存点赞记录
     * @param userLike
     * @return
     */
    void save(Like userLike);

    /**
     * 批量保存或修改
     * @param list
     */
    void saveAll(List<Like> list);


    /**
     * 根据文章的id查询点赞列表（即查询都谁给这篇文章点赞过）
     * @param articleId 文章的id
     * @return
     */
    List<Like> getLikedListByArticleId(int articleId);

    /**
     * 根据点赞人的ip查询点赞列表（即查询这个人都给谁点赞过）
     * @param ip
     * @return
     */
    List<Like> getLikedListByIp(String ip);

    /**
     * 通过文章的id和ip查询是否存在点赞记录
     * @param articleId
     * @param ip
     * @return
     */
    Like getByArticleAndIp(int articleId, String ip);


}
