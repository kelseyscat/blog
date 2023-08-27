package com.hcl.blog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hcl.blog.dao.ArticleDao;
import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.ArticleListExam;
import com.hcl.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> getAllArticleByDate(){
        List<Article> ale = articleDao.findArticles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        ListIterator<Article> iterator = ale.listIterator();  // 为了在遍历的时候直接修改值
        while(iterator.hasNext()){
            Article a = iterator.next();
            String formattedDate = sdf.format(a.getCreateTime());
            a.setFormattedDate(formattedDate);
            iterator.set(a);  // 修改元素的值
        }
        return ale;
    }

    @Override
    public Article getArticleById(int id) {
        return articleDao.findArticleById(id);
    }

    @Override
    public void insertNewArticle(String title, int status, Date createTime, String firstPic, String content,
                                 String contentText, String htmlContent, String description, String type) {
        articleDao.newArticle(title, status, createTime, firstPic, content, contentText, htmlContent, description, type);
    }

    @Override
    public void deleteArticle(int id) {
        articleDao.deleteArticle(id);
    }

    @Override
    public Set<String> findAllArticleType() {
        Set<String> res = articleDao.findAllArticleType();
        res.remove("default");
        return res;
    }

    @Override
    public void updateLikeNum(int id, int status) {
        articleDao.updateLikeNum(id, status);
    }
}
