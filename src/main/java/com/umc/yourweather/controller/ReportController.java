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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @Operation(summary = "주간 통계 요청", description = "이번 주로부터 n주 전(0도 포함=이번 주도 가능)의 데이터에 대한 통계를 얻을 수 있습니다. 단위는 Percentage(%)입니다.\n 개발 단계에서 요구 사항에 대한 잘못된 이해로 이렇게 n주 전 데이터도 얻을 수 있게 되었지만, 현 요구사항에 크게 어긋나는 것이 없고 추후 확장 가능성도 고려하여 그대로 뒀습니다. ++ 만약 모든 필드가 0으로 채워져서 온다면 그 주에 해당하는 데이터가 없는 것입니다.")
    @Parameter(name = "ago", description = "현재로부터 몇 주 전의 데이터를 얻을 지에 대한 파라미터입니다. default 값은 0(이번 주)입니다.", in = ParameterIn.QUERY)
    public ResponseDto<StatisticResponseDto> getWeeklyStatistic(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "0") int ago) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusWeeks(ago);

        Statistic statistic = reportService.getStatisticForWeek(customUserDetails.getUser(),
                dateTime);

        return ResponseDto.success(ago + "주 전 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/monthly-statistic")
    @Operation(summary = "월간 통계 요청", description = "이번 달로부터 n달 전(0도 포함=이번 달도 가능)의 데이터에 대한 통계를 얻을 수 있습니다. 단위는 Percentage(%)입니다.\n 개발 단계에서 요구 사항에 대한 잘못된 이해로 이렇게 n달 전 데이터도 얻을 수 있게 되었지만, 현 요구사항에 크게 어긋나는 것이 없고 추후 확장 가능성도 고려하여 그대로 뒀습니다. ++ 만약 모든 필드가 0으로 채워져서 온다면 그 달에 해당하는 데이터가 없는 것입니다.")
    @Parameter(name = "ago", description = "현재로부터 몇 달 전의 데이터를 얻을 지에 대한 파라미터입니다. default 값은 0(이번 달)입니다.", in = ParameterIn.QUERY)
    public ResponseDto<StatisticResponseDto> getThisMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "0") int ago) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = now.minusMonths(ago);

        Statistic statistic = reportService.getStatisticForMonth(customUserDetails.getUser(),
                dateTime);

        return ResponseDto.success(ago + "달 전 데이터 통계 요청 완료.", new StatisticResponseDto(statistic));
    }

    @GetMapping("/monthly-specific-weather")
    @Operation(summary = "월 중 특정 날씨 리스트 요청", description = "특정 월의 특정 날씨가 있던 날들을 가져옵니다. ex) 8월의 맑음 날씨 리스트 -> ~~ ")
    @Parameter(name = "month", description = "1월부터 12월 사이의 데이터를 요청하고 싶은 달", in = ParameterIn.QUERY)
    @Parameter(name = "weather", description = "요청하고 싶은 날씨 값. SUNNY, CLOUDY, RAINY, LIGHTNING 으로 구분합니다.", in = ParameterIn.QUERY)
    public ResponseDto<SpecificMemoResponseDto> getMonthlySpecificWeatherList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam int month,
            @RequestParam Status weather) {

        User user = customUserDetails.getUser();

        // getSpecificMemoList에 동일한 LocalDateTime이 있음.
        // 재사용 가능성이 있어보이니 나중에 리팩토링
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.now().getYear(),
                month,
                1,
                0,
                0,
                0);

        LocalDate endDate = startDateTime.withDayOfMonth(
                startDateTime.toLocalDate().lengthOfMonth()).toLocalDate();
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        List<MemoReportResponseDto> memoList = reportService.getSpecificWeatherList(user,
                weather, startDateTime, endDateTime);
        Statistic statistic = reportService.getStatisticForMonth(user, startDateTime);
        StatisticResponseDto statisticResDto = new StatisticResponseDto(statistic);

        SpecificMemoResponseDto result = SpecificMemoResponseDto.builder()
                .memoList(memoList)
                .proportion(statisticResDto.getProportion(weather))
                .build();

        return ResponseDto.success("월간 특정 날씨 일자 조회 성공."
                + startDateTime.toLocalDate() + "~" + endDateTime.toLocalDate()
                + "사이의 데이터", result);
    }

    @GetMapping("/weekly-specific-weather")
    @Operation(summary = "주 중 특정 날씨 리스트 요청", description = "지금으로부터 n주전의 특정 날씨가 있던 날들을 가져옵니다. ex) 7.30~8.5의 맑음 날씨 리스트 -> ~~ ")
    @Parameter(name = "week", description = "해당 파라미터만큼 이전 주의 특정 날씨들을 조회합니다.", in = ParameterIn.QUERY)
    @Parameter(name = "weather", description = "요청하고 싶은 날씨 값. SUNNY, CLOUDY, RAINY, LIGHTNING 으로 구분합니다.", in = ParameterIn.QUERY)
    public ResponseDto<SpecificMemoResponseDto> getWeeklySpecificWeatherList(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "0") int week,
            @RequestParam Status weather) {

        User user = customUserDetails.getUser();

        // getSpecificMemoList에 동일한 LocalDateTime이 있음.
        // 재사용 가능성이 있어보이니 나중에 리팩토링
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.minusWeeks(week);

        int dayOfWeek = dateTime.getDayOfWeek().getValue();

        LocalDate startDate = dateTime.toLocalDate().minusDays(dayOfWeek - 1);
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = dateTime.toLocalDate().plusDays(7 - dayOfWeek);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        List<MemoReportResponseDto> memoList = reportService.getSpecificWeatherList(user,
                weather, startDateTime, endDateTime);
        Statistic statistic = reportService.getStatisticForWeek(user, dateTime);
        StatisticResponseDto statisticResDto = new StatisticResponseDto(statistic);

        SpecificMemoResponseDto result = SpecificMemoResponseDto.builder()
                .memoList(memoList)
                .proportion(statisticResDto.getProportion(weather))
                .build();

        return ResponseDto.success(
                "주간 특정 날씨 일자 조회 성공."
                        + startDateTime.toLocalDate() + "~" + endDateTime.toLocalDate()
                        + "사이의 데이터", result);
    }

    @GetMapping("/weekly-comparison")
    @Operation(summary = "주간 데이터 비교", description = "비교하길 원하는 이전 주와 비교해 이번 주 감정은 어떤 것이 얼만큼 상승하고, 또 얼만큼 하락했는지 알려줍니다.\n ++부동소숫점 오차가 존재합니다. 물론 프론트에서 소숫점을 거르는 과정에서 처리가 되긴 할 겁니다.")
    @Parameter(name = "ago", description = "이번 주와 비교하고 싶은 특정 주. default 값은 1(1주 전)입니다.", in = ParameterIn.QUERY)
    public ResponseDto<StatisticResponseDto> getComparisonForWeek(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "1") int ago) {

        User user = customUserDetails.getUser();

        StatisticResponseDto result = reportService.getComparedWeeklyStatistic(user, ago);

        return ResponseDto.success("이번 주와 " + ago + "주 전 대비 지표 계산 완료", result);
    }

    @GetMapping("/monthly-comparison")
    @Operation(summary = "월간 데이터 비교", description = "비교하길 원하는 이전 달과 비교해 이번 달 감정은 어떤 것이 얼만큼 상승하고, 또 얼만큼 하락했는지 알려줍니다.\n ++부동소숫점 오차가 존재합니다. 물론 프론트에서 소숫점을 거르는 과정에서 처리가 되긴 할 겁니다.")
    @Parameter(name = "ago", description = "이번 달과 비교하고 싶은 특정 달. default 값은 1(1주 전)입니다.", in = ParameterIn.QUERY)
    public ResponseDto<StatisticResponseDto> getComparisonForMonth(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue = "1") int ago) {

        User user = customUserDetails.getUser();

        StatisticResponseDto result = reportService.getComparedMonthlyStatistic(user, ago);

        return ResponseDto.success("이번 달과 " + ago + "달 전 대비 지표 계산 완료", result);
    }
}
