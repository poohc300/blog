package com.example.blog.about.mapper;

import com.example.blog.about.model.About;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AboutMapper {
    About find();
    void upsert(About about);
}
