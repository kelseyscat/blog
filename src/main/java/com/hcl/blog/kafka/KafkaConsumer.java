package com.hcl.blog.kafka;

import com.alibaba.fastjson2.JSONObject;
import com.hcl.blog.entity.Like;
import com.hcl.blog.service.ArticleService;
import com.hcl.blog.service.LikeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    private LikeService likeService;

    @Autowired
    private ArticleService articleService;

    @KafkaListener(topics = "like_topic")
    public void dealLikeMessage(ConsumerRecord record) {
        if(record == null || record.value() == null){
           System.out.println("消息的内容为空！"); // log
           return;
        }
        Like like = JSONObject.parseObject(record.value().toString(), Like.class);
        if(like == null){
            System.out.println("消息格式错误！"); // log
//            logger.error("消息格式错误！");
            return;
        }
        // 同步数据库
        likeService.save(like);
        articleService.updateLikeNum(like.getArticleId(), like.getStatus());
    }
}
