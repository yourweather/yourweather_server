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
    public ResponseDto<StatisticResponseDto> getThisMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();

        Statistic statistic = reportService.getStatisticForMonth(customUserDetails.getUser(), now);

        return ResponseDto.success("이번 달 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/last-month")
    public ResponseDto<StatisticResponseDto> getLastMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime previousMonthDay = now.minusMonths(1);

        Statistic statistic = reportService.getStatisticForMonth(
                customUserDetails.getUser(), previousMonthDay);

        return ResponseDto.success("지난 달 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
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

        List<MemoReportResponseDto> memoList = reportService.getSpecificMemoList(user, status, month, dateTime);
        Statistic statistic = reportService.getStatisticForMonth(user, dateTime);
        StatisticResponseDto statisticResDto = new StatisticResponseDto(statistic);

        SpecificMemoResponseDto result = SpecificMemoResponseDto.builder()
                .memoList(memoList)
                .proportion(statisticResDto.getProportion(status))
                .build();

        return ResponseDto.success("월간 특정 날씨 일자 조회 성공.", result);
    }
}
