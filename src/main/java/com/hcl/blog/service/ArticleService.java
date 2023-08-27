package com.hcl.blog.service;

import com.github.pagehelper.PageInfo;
import com.hcl.blog.dao.ArticleDao;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ArticleListExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ArticleService {

    // 文章列表
    List<Article> getAllArticleByDate();

    Article getArticleById(int id);

    void insertNewArticle(String title, int status, Date createTime, String firstPic, String content, String contentText,
                          String htmlContent, String description, String type);

    void deleteArticle(int id);

    Set<String> findAllArticleType();

    void updateLikeNum(int id, int status);

}
