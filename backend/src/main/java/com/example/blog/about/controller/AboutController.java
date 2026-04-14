package com.example.blog.about.controller;

import com.example.blog.about.model.About;
import com.example.blog.about.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AboutController {

    private final AboutService aboutService;

    @GetMapping("/api/about")
    public ResponseEntity<About> get() {
        return ResponseEntity.ok(aboutService.get());
    }

    @PutMapping("/api/about")
    public ResponseEntity<Void> update(@RequestBody About about) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        aboutService.update(about);
        return ResponseEntity.ok().build();
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails;
    }
}
