package com.example.blog.comment.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentView {
    private Long id;
    private Long parentCommentId;
    private int depth;
    private String body;
    private String authorName;
    private String authorType;   // "user" | "guest"
    private boolean deleted;
    private LocalDateTime createdAt;
}
