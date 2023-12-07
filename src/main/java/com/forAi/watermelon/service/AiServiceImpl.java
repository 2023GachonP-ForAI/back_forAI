package com.forAi.watermelon.service;

import com.forAi.watermelon.dto.RecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {
    private final FileService fileService;

    private String url = "http://localhost:7000";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public RecordResponseDto requestAi(String fileName) {
        try {
            // RestTemplate를 사용하여 동기적으로 외부 API 요청
            ResponseEntity<Integer> response = restTemplate.getForEntity(
                    url + "/ai?record=" + fileName, // URL과 쿼리 파라미터
                    Integer.class // 응답 타입
            );

            // 응답 데이터 가져오기
            Integer responseData = response.getBody();

            // 필요한 경우 파일 삭제
//            fileService.deleteFile(fileName);

            // 응답 데이터를 이용하여 RecordResponseDto 객체 생성 및 반환
            return RecordResponseDto.builder()
                    .recordName(fileName)
                    .sweet(responseData)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("개인영상 분석중 오류: " + e.getMessage(), e);
        }
    }
}
