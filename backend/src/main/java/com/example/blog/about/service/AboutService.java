package com.example.blog.about.service;

import com.example.blog.about.mapper.AboutMapper;
import com.example.blog.about.model.About;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AboutService {

    private final AboutMapper aboutMapper;

    public About get() {
        return aboutMapper.find();
    }

    public void update(About about) {
        aboutMapper.upsert(about);
    }
}
