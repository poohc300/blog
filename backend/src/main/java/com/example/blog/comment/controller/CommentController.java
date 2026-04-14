package com.example.blog.comment.controller;

import com.example.blog.comment.model.CommentCreateRequest;
import com.example.blog.comment.model.CommentView;
import com.example.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentView>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getByPostId(postId));
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<Void> create(@PathVariable Long postId,
                                       @RequestBody CommentCreateRequest req) {
        Long adminUserId = getAdminUserId();
        commentService.create(postId, req, adminUserId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId,
                                       @RequestBody(required = false) Map<String, String> body) {
        boolean isAdmin = getAdminUserId() != null;
        String guestPassword = body != null ? body.get("guestPassword") : null;
        commentService.delete(commentId, guestPassword, isAdmin);
        return ResponseEntity.ok().build();
    }

    private Long getAdminUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails) {
            return 1L; // 단일 관리자
        }
        return null;
    }
}
