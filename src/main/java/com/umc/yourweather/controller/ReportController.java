package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.response.StatisticResponseDto;
import com.umc.yourweather.service.ReportService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.REPORT_URI)
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/this-week")
    public ResponseDto<StatisticResponseDto> getThisWeek(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();

        Statistic statistic = reportService.getStatisticForWeek(customUserDetails.getUser(), now);

        return ResponseDto.success("금주 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }


    @GetMapping("/last-week")
    public ResponseDto<StatisticResponseDto> getLastWeek(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeek = now.minusWeeks(1);

        Statistic statistic = reportService.getStatisticForWeek(customUserDetails.getUser(),
                lastWeek);

        return ResponseDto.success("이전 주 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/this-month")
    public ResponseDto<StatisticResponseDto> getThisMonth(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();

        Statistic statistic = reportService.getStatisticForMonth(customUserDetails.getUser(), now);

        return ResponseDto.success("이번 달 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/last-month")
    public ResponseDto<StatisticResponseDto> getLastMonth(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime previousMonthDay = now.minusMonths(1);

        Statistic statistic = reportService.getStatisticForMonth(
                customUserDetails.getUser(), previousMonthDay);

        return ResponseDto.success("지난 달 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }
}
