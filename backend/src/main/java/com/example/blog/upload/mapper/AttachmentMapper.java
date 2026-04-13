package com.example.blog.upload.mapper;

import com.example.blog.upload.model.Attachment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachmentMapper {
    void insert(Attachment attachment);
}
