package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.response.MemoReportResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.response.SpecificMemoResponseDto;
import com.umc.yourweather.response.StatisticResponseDto;
import com.umc.yourweather.service.ReportService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.REPORT_URI)
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/weekly-statistic")
    public ResponseDto<StatisticResponseDto> getWeeklyStatistic(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "0") int ago) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusWeeks(ago);

        Statistic statistic = reportService.getStatisticForWeek(customUserDetails.getUser(), dateTime);

        return ResponseDto.success(ago + "주 전 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/monthly-statistic")
    public ResponseDto<StatisticResponseDto> getThisMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "0") int ago) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusMonths(ago);

        Statistic statistic = reportService.getStatisticForMonth(customUserDetails.getUser(), dateTime);

        return ResponseDto.success("이번 달 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/list")
    public ResponseDto<SpecificMemoResponseDto> getSpecificMemoList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam int month,
            @RequestParam Status status) {
        User user = customUserDetails.getUser();

        // getSpecificMemoList에 동일한 LocalDateTime이 있음.
        // 재사용 가능성이 있어보이니 나중에 리팩토링
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.now().getYear(),
                month,
                1,
                0,
                0,
                0);

        List<MemoReportResponseDto> memoList = reportService.getSpecificMemoList(user, status, dateTime);
        Statistic statistic = reportService.getStatisticForMonth(user, dateTime);
        StatisticResponseDto statisticResDto = new StatisticResponseDto(statistic);

        SpecificMemoResponseDto result = SpecificMemoResponseDto.builder()
                .memoList(memoList)
                .proportion(statisticResDto.getProportion(status))
                .build();

        return ResponseDto.success("월간 특정 날씨 일자 조회 성공.", result);
    }

    @GetMapping("/weekly-comparison")
    public ResponseDto<StatisticResponseDto> getComparisonForWeek(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "1") int week) {

        User user = customUserDetails.getUser();

        StatisticResponseDto result = reportService.getComparedWeeklyStatistic(user, week);

        return ResponseDto.success("주간 대비 지표 계산 완료", result);
    }

    @GetMapping("/monthly-comparison")
    public ResponseDto<StatisticResponseDto> getComparisonForMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "1") int month) {

        User user = customUserDetails.getUser();

        StatisticResponseDto result = reportService.getComparedMonthlyStatistic(user, month);

        return ResponseDto.success("월간 대비 지표 계산 완료", result);
    }
}
