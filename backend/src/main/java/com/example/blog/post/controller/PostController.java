package com.example.blog.post.controller;

import com.example.blog.post.model.Post;
import com.example.blog.post.model.PostCreateRequest;
import com.example.blog.post.model.PostUpdateRequest;
import com.example.blog.post.model.Topic;
import com.example.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        Post post = postService.getById(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/api/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(postService.getAllTopics());
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Void> create(@RequestBody PostCreateRequest request) {
        postService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody PostUpdateRequest request) {
        postService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
