package com.example.blog.upload.controller;

import com.example.blog.upload.mapper.AttachmentMapper;
import com.example.blog.upload.model.Attachment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final AttachmentMapper attachmentMapper;

    @Value("${upload.dir:/home/jeremy/dev/blog/uploads}")
    private String uploadDir;

    @Value("${upload.url-prefix:/uploads}")
    private String urlPrefix;

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();
        String ext = originalName != null && originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";
        String storedName = UUID.randomUUID() + ext;

        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);
        Path target = dir.resolve(storedName);
        file.transferTo(target);

        Attachment attachment = new Attachment();
        attachment.setOriginalName(originalName != null ? originalName : storedName);
        attachment.setStoredName(storedName);
        attachment.setFilePath(target.toString());
        attachment.setFileSize(file.getSize());
        attachment.setMimeType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
        attachment.setUploadedBy(1L); // TODO: 로그인 유저 연결
        attachmentMapper.insert(attachment);

        String url = urlPrefix + "/" + storedName;
        return ResponseEntity.ok(Map.of("url", url));
    }
}
