package com.umc.yourweather.response;

import com.umc.yourweather.domain.Statistic;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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
}
