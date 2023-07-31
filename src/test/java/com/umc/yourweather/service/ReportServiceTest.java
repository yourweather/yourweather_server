package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.test.MemoTestRepository;
import java.time.LocalDateTime;
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
    @DisplayName("getStatisticForWeek 작동 확인")
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
    @DisplayName("getStatisticForMonth 작동 확인")
    public void test2() {
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

}