package com.example.blog.auth.mapper;

import com.example.blog.auth.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    User findById(Long id);
    void insert(User user);
}
