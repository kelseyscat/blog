package com.hcl.blog.service.Impl;

import com.hcl.blog.dao.CatecoryDao;
import com.hcl.blog.entity.Category;
import com.hcl.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CatecoryDao catecoryDao;

    @Override
    public List<Category> findArticleCategories(int articleId) {
        return catecoryDao.findCategorysWithArticleId(articleId);
    }
}
