package com.hcl.blog.service.Impl;

import com.hcl.blog.dao.ArticleDao;
import com.hcl.blog.dao.LikeDao;
import com.hcl.blog.entity.Like;
import com.hcl.blog.service.LikeService;
import com.hcl.blog.util.RedisKeyUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int getLikeNum(int articleId) {
        return articleDao.countLikeNum(articleId);
    }

    @Override
    public List<Like> getLikeInfoByArticleId(int articleId) {
        return articleDao.getLikeInfoByArticleId(articleId);
    }

    @Override
    @Transactional
    public void save(Like userLike) {
        likeDao.save(userLike);
    }

    @Override
    @Transactional
    public void saveAll(List<Like> list) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        list.stream().forEach(likeInfo -> save(likeInfo));  // 批处理
        sqlSession.commit();
        sqlSession.clearCache();
    }

    @Override
    public List<Like> getLikedListByArticleId(int articleId) {
        return likeDao.findByArticleIdAndStatus(articleId, 1);
    }

    @Override
    public List<Like> getLikedListByIp(String ip) {
        return likeDao.findByIpAndStatus(ip, 1);
    }

    @Override
    public Like getByArticleAndIp(int articleId, String ip) {
        return likeDao.findByArticleAndIp(articleId, ip);
    }

}
