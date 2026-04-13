package com.example.blog.post.service;

import com.example.blog.post.mapper.PostMapper;
import com.example.blog.post.model.Post;
import com.example.blog.post.model.PostCreateRequest;
import com.example.blog.post.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public List<Post> getAll() {
        return postMapper.findAll();
    }

    public Post getById(Long id) {
        return postMapper.findById(id);
    }

    public List<Topic> getAllTopics() {
        return postMapper.findAllTopics();
    }

    public void create(PostCreateRequest request) {
        postMapper.insert(request);
    }
}
