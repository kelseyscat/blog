package com.hcl.blog.controller;

import com.hcl.blog.entity.Article;
import com.hcl.blog.entity.Like;
import com.hcl.blog.kafka.KafkaConsumer;
import com.hcl.blog.kafka.KafkaProducer;
import com.hcl.blog.service.LikeRedisService;
import com.hcl.blog.service.LikeService;
import com.hcl.blog.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRedisService likeRedisService;

    @Autowired
    private KafkaProducer kafkaProducer;

//    @RequestMapping(value = "/likeNum", method = RequestMethod.POST)
//    public void likeNum(int articleId, HttpServletResponse response) throws IOException {
//        System.out.println(articleId);
//
//        long cnt = likeService.getLikeNum(articleId);
//
//        JSONObject json= new JSONObject();
//
//        json.put("likeCnt", cnt);
//
//        response.getWriter().print(json);
//    }

    @RequestMapping(value = "/toClickThumbsup", method = RequestMethod.POST)
    public void clickThumbsup(int articleId, boolean ifCancelThumbsup, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取 ip 地址
        String ip = IpUtil.getIpAddress(request);

        if(!ifCancelThumbsup){  // 赞了
            likeRedisService.saveLiked2Redis(articleId, ip);
            likeRedisService.incrementLikedCount(articleId);
        }
        else{  // 取消了
            likeRedisService.unlikeFromRedis(articleId, ip);
            likeRedisService.decrementLikedCount(articleId);
        }
        // 从redis获取点赞数
        Integer cnt = likeRedisService.getLikedCount(articleId);
        if(cnt == null){
            cnt = likeService.getLikeNum(articleId);
        }

        // 同步数据库的操作放到redis之后执行，不能让用户去承担这个时间
//        int status = !ifCancelThumbsup ? 1 : -1;
//        Like like = new Like(articleId, ip, status);
//        kafkaProducer.sendLikeEvent(like);

        JSONObject json= new JSONObject();

        json.put("likeCnt", cnt);

        response.getWriter().print(json);
    }

}
