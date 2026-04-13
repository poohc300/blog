package com.example.blog.post.mapper;

import com.example.blog.post.model.Post;
import com.example.blog.post.model.PostCreateRequest;
import com.example.blog.post.model.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findAll();
    Post findById(Long id);
    List<Topic> findAllTopics();
    void insert(PostCreateRequest request);
}
