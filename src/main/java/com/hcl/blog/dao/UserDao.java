package com.hcl.blog.dao;

import com.hcl.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface UserDao {
    User findByUser(@Param("username") String username);

    User findByUserId(@Param("userid") int id);

    Date getOriginDate(@Param("userid") int id);
}
