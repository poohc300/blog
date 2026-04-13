package com.example.blog.upload.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Attachment {
    private Long id;
    private Long contentId;
    private String originalName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private Long uploadedBy;
    private LocalDateTime createdAt;
}
