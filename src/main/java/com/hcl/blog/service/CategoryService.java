package com.hcl.blog.service;

import com.hcl.blog.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findArticleCategories(int articleId);

}
