package com.umc.yourweather.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.test.MemoTestRepository;
import com.umc.yourweather.response.StatisticResponseDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class StatisticCreatorTest {

    MemoRepository memoRepository = new MemoTestRepository(7);

    @BeforeEach
    public void setMemoRepository() {
        memoRepository = new MemoTestRepository(7);
    }

    @Test
    @DisplayName("주간 통계 수치화 테스트")
    public void test1() {
        // given
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);
        User user = User.builder()
                .email("sbs8239@gmail.com")
                .build();

        // when
        Statistic weeklyStatistic = statisticCreator.createWeeklyStatistic(user, LocalDateTime.now());
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(weeklyStatistic);
        int sum = Math.round(statisticResponseDto.getSunny() +
                statisticResponseDto.getCloudy() +
                statisticResponseDto.getRainy() +
                statisticResponseDto.getLightning());

        // then
        System.out.println(weeklyStatistic);
        System.out.println(statisticResponseDto.toString());

        assertEquals(weeklyStatistic.getSum(), 7);
        assertEquals(sum, 100);
    }

    @Test
    @DisplayName("월간 통계 수치화 테스트")
    public void test2() {
        // given
        memoRepository = new MemoTestRepository(31);
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);
        User user = User.builder()
                .email("sbs8239@gmail.com")
                .build();

        // when
        Statistic weeklyStatistic = statisticCreator.createWeeklyStatistic(user, LocalDateTime.now());
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(weeklyStatistic);
        int sum = Math.round(statisticResponseDto.getSunny() +
                statisticResponseDto.getCloudy() +
                statisticResponseDto.getRainy() +
                statisticResponseDto.getLightning());

        // then
        System.out.println(weeklyStatistic);
        System.out.println(statisticResponseDto.toString());

        assertEquals(weeklyStatistic.getSum(), 31);
        assertEquals(sum, 100);
    }
}