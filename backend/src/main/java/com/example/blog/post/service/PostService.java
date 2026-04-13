package com.example.blog.post.service;

import com.example.blog.post.mapper.PostMapper;
import com.example.blog.post.model.Post;
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
}
