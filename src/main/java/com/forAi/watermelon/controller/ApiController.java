package com.forAi.watermelon.controller;

import com.forAi.watermelon.dto.RecordRequestDto;
import com.forAi.watermelon.dto.RecordResponseDto;
import com.forAi.watermelon.service.AiService;
import com.forAi.watermelon.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final FileService fileService;
    private final AiService aiService;

    @PostMapping(value = "/result/sweet")
    public Mono<ResponseEntity<?>> recordSend(@RequestPart MultipartFile file) {

            System.out.println(file.isEmpty());
            System.out.println(file.getOriginalFilename());
            String fileName = fileService.saveFile(file);
            Mono<RecordResponseDto> dto = aiService.requestAi(fileName);
            return dto.map(recordResponseDto ->
                    ResponseEntity.status(HttpStatus.OK).body(recordResponseDto));

    }


}
