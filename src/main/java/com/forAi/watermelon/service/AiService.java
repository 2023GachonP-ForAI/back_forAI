package com.forAi.watermelon.service;

import com.forAi.watermelon.dto.RecordResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface AiService {
    RecordResponseDto requestAi(String fileName);

}
