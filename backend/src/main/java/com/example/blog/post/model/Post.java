package com.example.blog.post.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private Long authorId;
    private String title;
    private String summary;
    private String body;
    private LocalDateTime createdAt;
    private String tag;
    private int viewCount;
}
