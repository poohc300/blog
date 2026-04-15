package com.example.blog.post.mapper;

import com.example.blog.post.model.Post;
import com.example.blog.post.model.PostCreateRequest;
import com.example.blog.post.model.PostUpdateRequest;
import com.example.blog.post.model.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findAll();
    List<Post> search(@Param("q") String q);
    Post findById(Long id);
    List<Topic> findAllTopics();
    void insert(PostCreateRequest request);
    void update(@Param("id") Long id, @Param("req") PostUpdateRequest request);
    void deleteGuestsByPostId(Long id);
    void deleteCommentersByPostId(Long id);
    void deleteCommentsByPostId(Long id);
    void deleteAttachmentsByPostId(Long id);
    void deleteById(Long id);
}
