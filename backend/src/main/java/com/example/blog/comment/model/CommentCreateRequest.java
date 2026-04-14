package com.example.blog.comment.model;

import lombok.Data;

@Data
public class CommentCreateRequest {
    private Long parentCommentId;
    private String body;
    private String guestName;
    private String guestPassword;
}
