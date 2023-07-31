package com.umc.yourweather.domain;

import com.umc.yourweather.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@ToString
@NoArgsConstructor
public class Statistic {

    private int sunny;
    private int cloudy;
    private int rainy;
    private int lightning;


    public void plusPoint(Status status) {
        switch(status){
            case SUNNY -> sunny++;
            case CLOUDY -> cloudy++;
            case RAINY -> rainy++;
            case LIGHTNING -> lightning++;
            default -> log.error("통계 값 갱신 실패: 올바른 status 값이 아닙니다.");
        }
    }

    public int getSum() {
        return sunny + cloudy + rainy + lightning;
    }
}
