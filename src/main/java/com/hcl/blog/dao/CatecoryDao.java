package com.hcl.blog.dao;

import com.hcl.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CatecoryDao {

    List<Category> findCategorysWithArticleId(@Param("articleId") int articleId);

}
