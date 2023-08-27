package com.hcl.blog.dao;

import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface ArticleDao {

    List<Article> findArticles();

    Long countArticles();

    Article findArticleById(@Param("id") int id);

    void newArticle(@Param("title") String title, @Param("status") int status,
                    @Param("createTime") Date createTime, @Param("firstPic") String firstPic,
                    @Param("content") String content, @Param("contentText") String contentText,
                    @Param("htmlContent") String htmlContent, @Param("description") String description,
                    @Param("type") String type);

    void deleteArticle(@Param("id") int id);

    Set<String> findAllArticleType();

    int countLikeNum(@Param("articleId") int articleId);

    List<Like> getLikeInfoByArticleId(@Param("articleId") int articleId);

    void updateLikeNum(@Param("articleId") int id, @Param("status") int status);
}
