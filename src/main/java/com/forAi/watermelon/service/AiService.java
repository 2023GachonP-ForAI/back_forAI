package com.forAi.watermelon.service;

import com.forAi.watermelon.dto.RecordResponseDto;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface AiService {
    Mono<RecordResponseDto> requestAi(String fileName);

    Mono<RecordResponseDto> requestDefault(String goodRecord);
}
