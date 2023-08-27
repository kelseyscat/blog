package com.hcl.blog.dao;

import com.hcl.blog.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeDao {

//    void save(@Param("articleId") int articleId, @Param("ip") String ip, @Param("status") int status);

    void save(@Param("userLike") Like like);

    List<Like> findByArticleIdAndStatus(int articleId, int status);

    List<Like> findByIpAndStatus(String ip, int status);

    Like findByArticleAndIp(int articleId, String ip);

}
