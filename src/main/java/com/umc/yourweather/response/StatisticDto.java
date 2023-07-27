package com.umc.yourweather.response;

import com.umc.yourweather.domain.Statistic;
import lombok.Getter;

@Getter
public class StatisticDto {

    // 단위는 percentage(%).
    private final float sunny;
    private final float cloudy;
    private final float rainy;
    private final float lightning;

    public StatisticDto(Statistic statistic) {
        int sum = statistic.getSum();
        sunny = ((float) statistic.getSunny()) / sum;
        cloudy = ((float) statistic.getSunny()) / sum;
        rainy = ((float) statistic.getSunny()) / sum;
        lightning = ((float) statistic.getSunny()) / sum;
    }
}
