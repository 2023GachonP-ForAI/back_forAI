package com.forAi.watermelon.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile file);

    void deleteFile(String fileName);
}