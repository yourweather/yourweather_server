package com.umc.yourweather.domain;

import com.umc.yourweather.domain.enums.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Statistic {

    int sunny;
    int cloudy;
    int rainy;
    int lightning;

    public void plusPoint(Status status) {
        switch(status){
            case SUNNY -> sunny++;
            case CLOUDY -> cloudy++;
            case RAINY -> rainy++;
            case LIGHTNING -> lightning++;
            default -> log.error("통계 값 갱신 실패: 올바른 status 값이 아닙니다.");
        }
    }
}
