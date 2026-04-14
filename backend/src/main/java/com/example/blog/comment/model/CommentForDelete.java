package com.example.blog.comment.model;

import lombok.Data;

@Data
public class CommentForDelete {
    private Long id;
    private Long commenterId;
    private String commenterType;
    private String guestPasswordHash;
    private boolean deleted;
    private int depth;
}
