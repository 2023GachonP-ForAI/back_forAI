package com.forAi.watermelon.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class RecordResponseDto {
    private String recordName;
    private Integer sweet;

    @Builder
    public RecordResponseDto(String recordName, Integer sweet) {
        this.recordName = recordName;
        this.sweet = sweet;
    }
}
