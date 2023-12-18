package com.forAi.watermelon.entity;

import com.forAi.watermelon.constant.ConstSweet;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Sweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sweetSeq;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private ConstSweet sweet;

    @Builder
    public Sweet(String fileName, Integer sweet) {
        if (sweet == 1) {
            this.sweet = ConstSweet.GOOD;
        } else if (sweet == 0) {
            this.sweet = ConstSweet.BAD;
        } else {
            throw new IllegalArgumentException();
        }
        this.fileName = fileName;
    }

}
