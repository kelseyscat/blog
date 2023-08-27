package com.hcl.blog;

import com.hcl.blog.entity.Like;
import com.hcl.blog.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class test {

    @Autowired
    private LikeService likeService;

    @Test
    public void testMyServiceMethod() {
        Like like = new Like(9, "127.0.0.1", 1);
        likeService.save(like);
    }


}
