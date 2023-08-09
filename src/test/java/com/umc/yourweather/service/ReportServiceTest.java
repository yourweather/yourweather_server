package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.Proportion;
import com.umc.yourweather.domain.Proportion.Builder;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.test.MemoTestRepository;
import com.umc.yourweather.response.MemoReportResponseDto;
import com.umc.yourweather.response.StatisticResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportServiceTest {

    MemoRepository memoRepository = new MemoTestRepository(7);
    ReportService reportService = new ReportService(memoRepository);

    @BeforeEach
    public void setMemoRepository() {
        memoRepository = new MemoTestRepository(7);
        reportService = new ReportService(memoRepository);
    }

    @Test
    @DisplayName("getStatisticForWeek: 작동 확인")
    public void test1() {
        // given
        // 테스트용 레포지토리는 user, datetime에 영향을 받지 않음.
        // 이 테스트는 유저와 잘 연결되는지 보려고 하는 것이 아닌, 그저 통계가 잘 나오는지 보려는 테스트이기 때문에
        // 더미 값으로 둔다.
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        Statistic statistic = reportService.getStatisticForWeek(user, now);

        // then
        System.out.println(statistic);
        assertEquals(7, statistic.getSum());
    }

    @Test
    @DisplayName("getStatisticForWeek: 평균 값 확인 1")
    public void test2() {
        // given
        Proportion proportion = new Builder()
                .sunny(10)
                .cloudy(10)
                .rainy(10)
                .lightning(10)
                .build();
        memoRepository = new MemoTestRepository(proportion);
        reportService = new ReportService(memoRepository);
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        Statistic statisticForWeek = reportService.getStatisticForWeek(user, now);
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(statisticForWeek);

        // then
        assertEquals(25, statisticResponseDto.getSunny());
        assertEquals(25, statisticResponseDto.getCloudy());
        assertEquals(25, statisticResponseDto.getRainy());
        assertEquals(25, statisticResponseDto.getLightning());
    }

    @Test
    @DisplayName("getStatisticForWeek: 평균 값 확인 2")
    public void test3() {
        // given
        Proportion proportion = new Builder()
                .sunny(3)
                .cloudy(8)
                .rainy(6)
                .lightning(2)
                .build();
        memoRepository = new MemoTestRepository(proportion);
        reportService = new ReportService(memoRepository);
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        Statistic statisticForWeek = reportService.getStatisticForWeek(user, now);
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(statisticForWeek);

        // then
        assertEquals(15, (int) statisticResponseDto.getSunny());
        assertEquals(42, (int) statisticResponseDto.getCloudy());
        assertEquals(31, (int) statisticResponseDto.getRainy());
        assertEquals(10, (int) statisticResponseDto.getLightning());
    }

    @Test
    @DisplayName("getStatisticForMonth 작동 확인")
    public void test4() {
        // given
        // 테스트용 레포지토리는 user, datetime에 영향을 받지 않음.
        // 이 테스트는 유저와 잘 연결되는지 보려고 하는 것이 아닌, 그저 통계가 잘 나오는지 보려는 테스트이기 때문에
        // 더미 값으로 둔다.
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();
        memoRepository = new MemoTestRepository(31);
        reportService = new ReportService(memoRepository);

        // when
        Statistic statistic = reportService.getStatisticForMonth(user, now);

        // then
        System.out.println(statistic);
        assertEquals(31, statistic.getSum());
    }

    @Test
    @DisplayName("getStatisticForMonth: 평균 값 확인 1")
    public void test5() {
        // given
        Proportion proportion = new Builder()
                .sunny(10)
                .cloudy(10)
                .rainy(10)
                .lightning(10)
                .build();
        memoRepository = new MemoTestRepository(proportion);
        reportService = new ReportService(memoRepository);
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        Statistic statisticForWeek = reportService.getStatisticForMonth(user, now);
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(statisticForWeek);

        // then
        assertEquals(25, statisticResponseDto.getSunny());
        assertEquals(25, statisticResponseDto.getCloudy());
        assertEquals(25, statisticResponseDto.getRainy());
        assertEquals(25, statisticResponseDto.getLightning());
    }

    @Test
    @DisplayName("getStatisticForMonth: 평균 값 확인 2")
    public void test6() {
        // given
        Proportion proportion = new Builder()
                .sunny(3)
                .cloudy(8)
                .rainy(6)
                .lightning(2)
                .build();
        memoRepository = new MemoTestRepository(proportion);
        reportService = new ReportService(memoRepository);
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        Statistic statisticForWeek = reportService.getStatisticForMonth(user, now);
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(statisticForWeek);

        // then
        assertEquals(15, (int) statisticResponseDto.getSunny());
        assertEquals(42, (int) statisticResponseDto.getCloudy());
        assertEquals(31, (int) statisticResponseDto.getRainy());
        assertEquals(10, (int) statisticResponseDto.getLightning());
    }

    @Test
    @DisplayName("getSpecificMemoList: 현재 주")
    public void test7() {
        // given
        final int sunnyNum = 10;
        final int cloudyNum = 10;
        final int rainyNum = 10;
        final int lightningNum = 13;
        Proportion proportion = new Builder()
                .sunny(sunnyNum)
                .cloudy(cloudyNum)
                .rainy(rainyNum)
                .lightning(lightningNum)
                .build();
        memoRepository = new MemoTestRepository(proportion);
        reportService = new ReportService(memoRepository);
        User user = User.builder().build();
        LocalDateTime now = LocalDateTime.now();

        // when
        List<MemoReportResponseDto> sunnyList = reportService
                .getSpecificMemoList(user, Status.SUNNY, now);
        List<MemoReportResponseDto> cloudyList = reportService
                .getSpecificMemoList(user, Status.CLOUDY, now);
        List<MemoReportResponseDto> rainyList = reportService
                .getSpecificMemoList(user, Status.RAINY, now);
        List<MemoReportResponseDto> lightningList = reportService
                .getSpecificMemoList(user, Status.LIGHTNING, now);

        // then
        assertEquals(sunnyNum, sunnyList.size());
        assertEquals(cloudyNum, cloudyList.size());
        assertEquals(rainyNum, rainyList.size());
        assertEquals(lightningNum, lightningList.size());
    }
}