package com.umc.yourweather.domain;

import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.test.MemoTestRepository;
import com.umc.yourweather.response.StatisticResponseDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatisticCreatorTest {

    MemoRepository memoRepository = new MemoTestRepository();

    @Test
    @DisplayName("통계 생성 테스트")
    public void test1() {
        // given
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);

        // when
        Statistic weeklyStatistic = statisticCreator.createWeeklyStatistic(LocalDateTime.now());

        // then
        System.out.println(weeklyStatistic.toString());
    }

    @Test
    @DisplayName("통계 수치화 테스트")
    public void test2() {
        // given
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);

        // when
        Statistic weeklyStatistic = statisticCreator.createWeeklyStatistic(LocalDateTime.now());
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(weeklyStatistic);

        // then
        System.out.println(weeklyStatistic);
        System.out.println(statisticResponseDto.toString());
    }
}