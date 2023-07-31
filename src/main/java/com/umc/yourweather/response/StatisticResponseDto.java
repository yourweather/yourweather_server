package com.umc.yourweather.response;

import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
@AllArgsConstructor
@Builder
public class StatisticResponseDto {

    // 단위는 percentage(%).
    private final float sunny;
    private final float cloudy;
    private final float rainy;
    private final float lightning;

    public StatisticResponseDto(Statistic statistic) {
        int sum = statistic.getSum();
        sunny = ((float) statistic.getSunny()) / sum * 100;
        cloudy = ((float) statistic.getCloudy()) / sum * 100;
        rainy = ((float) statistic.getRainy()) / sum * 100;
        lightning = ((float) statistic.getLightning()) / sum * 100;
    }

    public float getProportion(Status status) {
        float returnValue = 0f;
        switch (status) {
            case SUNNY -> returnValue = sunny;
            case CLOUDY -> returnValue = cloudy;
            case RAINY -> returnValue = rainy;
            case LIGHTNING -> returnValue = lightning;
            default -> log.error("리턴 값 갱신 실패: 올바른 status 값이 아닙니다.");
        }

        return returnValue;
    }

    public StatisticResponseDto compareWith(StatisticResponseDto statistic) {
        return StatisticResponseDto.builder()
                .sunny(this.sunny - statistic.getSunny())
                .cloudy(this.cloudy - statistic.getCloudy())
                .rainy(this.rainy - statistic.getRainy())
                .lightning(this.lightning - statistic.getLightning())
                .build();
    }
}
