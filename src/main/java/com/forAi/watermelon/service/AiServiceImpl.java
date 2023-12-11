package com.forAi.watermelon.service;

import com.forAi.watermelon.dto.RecordResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final String url = "http://localhost:7000";

    WebClient webClient = WebClient.create(url);
    private RestTemplate restTemplate;

    public AiServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Mono<RecordResponseDto> requestAi(String fileName) {
        try {
            return webClient.get().uri(uriBuilder -> uriBuilder
                            .path("/ai")
                            .queryParam("record", fileName)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .doOnNext(resMap -> {
                        System.out.println("받은 값 : " + resMap + "\nfile name : " + fileName);
                    })
                    .map(resMap -> {
                                Integer sweetValue = (Integer) resMap.get("sweet");
                                return RecordResponseDto
                                        .builder()
                                        .recordName(fileName)
                                        .sweet(sweetValue)
                                        .build();
                            });
                            // 필요한 경우 파일 삭제
//            fileService.deleteFile(fileName);
        } catch (Exception e) {
            throw new RuntimeException("녹음 분석중 오류: " + e.getMessage());
        }
    }
}
