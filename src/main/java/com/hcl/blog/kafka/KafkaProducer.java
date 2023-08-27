package com.hcl.blog.kafka;

import com.alibaba.fastjson2.JSONObject;
import com.hcl.blog.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendLikeEvent(Like like) {
        kafkaTemplate.send("like_topic", JSONObject.toJSONString(like));
    }
}
