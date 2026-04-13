package com.example.blog.post.model;

import lombok.Data;

@Data
public class PostCreateRequest {
    private Long topicId;
    private String title;
    private String body;
    private Long authorId;
}
