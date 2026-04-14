package com.example.blog.post.model;

import lombok.Data;

@Data
public class PostUpdateRequest {
    private Long topicId;
    private String title;
    private String body;
}
