package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.response.StatisticDto;
import com.umc.yourweather.service.ReportService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.REPORT_URI)
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/this-week")
    public ResponseDto<StatisticDto> getThisWeek() {
        LocalDateTime now = LocalDateTime.now();
        Statistic statistic = reportService.getStatisticForWeek(now);

        return ResponseDto.success("금주 데이터 통계 요청 완료.", new StatisticDto(statistic));
    }
}
