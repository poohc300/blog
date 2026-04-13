package com.example.blog.post.mapper;

import com.example.blog.post.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findAll();
    Post findById(Long id);
}
