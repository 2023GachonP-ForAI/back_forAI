package com.forAi.watermelon.controller;

import com.forAi.watermelon.dto.RecordRequestDto;
import com.forAi.watermelon.service.AiService;
import com.forAi.watermelon.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final FileService fileService;
    private final AiService aiService;

    @PostMapping(value = "/record/send")
    public ResponseEntity<?> recordSend(@RequestPart MultipartFile recordFile) {
        try {
            String fileName = fileService.saveFile(recordFile);
            return ResponseEntity.status(HttpStatus.OK).body(aiService.requestAi(fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
