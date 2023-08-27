package com.hcl.blog.controller;

import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.Like;
import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.LikeRedisService;
import com.hcl.blog.service.LikeService;
import com.hcl.blog.util.CookieUtil;
import com.hcl.blog.util.IpUtil;
import io.swagger.models.auth.In;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleDetailController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRedisService likeRedisService;

//    @RequestMapping(value = "/displayArticle", method = RequestMethod.GET)
//    public String getArticleDetail(){
//        return "article";
//    }

    @RequestMapping(value = "/displayArticle", method = RequestMethod.GET)
    public String getArticleDetail(@RequestParam int id, HttpServletRequest request){
        Article a = articleService.getArticleById(id);
        request.setAttribute("article", a);
        // 获取 ip 地址
        String ip = IpUtil.getIpAddress(request);
        // 判断是否点过赞
        boolean is_like = false;
        // 先查Redis
        Integer status = likeRedisService.getLikeDataFromRedis(id, ip);
        if(status != null && status == 1){
            is_like = true;
        }
        else{
            // 查不到再查DB
            Like resFromDb = likeService.getByArticleAndIp(id, ip);
            if(resFromDb != null){
                if(resFromDb.getStatus() == 1){  // 记录中点过赞
                    is_like = true;
                    // 同步redis
                    likeRedisService.saveLiked2Redis(id, ip);
                }
                else{  // 没点
                    likeRedisService.unlikeFromRedis(id, ip);
                }
            }
        }
        request.setAttribute("is_like", is_like);

        Integer like_cnt = likeRedisService.getLikedCount(id); // 先查redis
        if(like_cnt == null){
            like_cnt = a.getLikeNum(); // 查数据库同步db
            likeRedisService.setLikedCount(id, like_cnt);
        }
        request.setAttribute("like_cnt", like_cnt);

        return "article";
    }

}
