package com.hcl.blog.service.Impl;

import com.hcl.blog.dao.ArticleDao;
import com.hcl.blog.dao.CommentDao;
import com.hcl.blog.dao.UserDao;
import com.hcl.blog.entity.DashBoardStatistic;
import com.hcl.blog.service.DashboardService;
import com.hcl.blog.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public DashBoardStatistic getDashBoardStatistics() {
        DashBoardStatistic st = new DashBoardStatistic();

        Long articles = articleDao.countArticles();

        long readingVolume = 0;

        for(int i = 1; i <= articles; i ++ ){
            String readVolumeKey = RedisKeyUtil.getReadingVolumeKey(i);
            Object num = redisTemplate.opsForValue().get(readVolumeKey);
            if(num != null){
                readingVolume += (long) num;
            }
        }

        Long comments = commentDao.countComments();

        Date originDate = userDao.getOriginDate(1);
        Date now = new Date(); // 当前日期

        long from = originDate.getTime();
        long to = now.getTime();
        long days = (to - from) / (1000 * 60 * 60 * 24);  // 除以 1000 因为 getTime() 得到的是毫秒数

        st.setArticles(articles);
        st.setComments(comments);
        st.setReadingVolume(readingVolume);
        st.setCreateDay(days);

        return st;
    }

}
