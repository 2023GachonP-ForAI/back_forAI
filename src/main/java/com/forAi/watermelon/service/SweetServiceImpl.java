package com.forAi.watermelon.service;

import com.forAi.watermelon.dto.RecordResponseDto;
import com.forAi.watermelon.entity.Sweet;
import com.forAi.watermelon.repository.SweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SweetServiceImpl implements SweetService {

    private final SweetRepository sweetRepository;

    @Override
    public void saveSweet(RecordResponseDto dto) {
        Sweet sweet = Sweet.builder()
                .fileName(dto.getRecordName())
                .sweet(dto.getSweet())
                .build();

        sweetRepository.save(sweet);
    }
}
