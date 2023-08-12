package com.umc.yourweather.service;

import com.umc.yourweather.domain.StatisticCreator;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.response.MemoReportResponseDto;
import com.umc.yourweather.response.StatisticResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final MemoRepository memoRepository;

    public Statistic getStatisticForWeek(User user, LocalDateTime dateTime) {
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);

        return statisticCreator.createWeeklyStatistic(user, dateTime);
    }

    public Statistic getStatisticForMonth(User user, LocalDateTime dateTime) {
        StatisticCreator statisticCreator = new StatisticCreator(memoRepository);

        return statisticCreator.createMonthlyStatistic(user, dateTime);
    }

    public List<MemoReportResponseDto> getMonthlySpecificWeatherList(User user, Status status, LocalDateTime startDateTime) {
        LocalDate endDate = startDateTime.withDayOfMonth(
                startDateTime.toLocalDate().lengthOfMonth()).toLocalDate();
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        List<Memo> memoList = memoRepository.findSpecificMemoList(user, status, startDateTime, endDateTime);

        return memoList.stream()
                .map(MemoReportResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MemoReportResponseDto> getWeeklySpecificWeatherList(User user, Status status, LocalDateTime dateTime) {

        int dayOfWeek = dateTime.getDayOfWeek().getValue();

        LocalDate startDate = dateTime.toLocalDate().minusDays(dayOfWeek - 1);
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = dateTime.toLocalDate().plusDays(7 - dayOfWeek);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        List<Memo> memoList = memoRepository.findSpecificMemoList(user, status, startDateTime, endDateTime);

        return memoList.stream()
                .map(MemoReportResponseDto::new)
                .collect(Collectors.toList());
    }

    public StatisticResponseDto getComparedWeeklyStatistic(User user, int week) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime toCompareWeek = now.minusWeeks(week);

        Statistic thisWeek = getStatisticForWeek(user, now);
        Statistic toCompareWeekStatistic = getStatisticForWeek(user, toCompareWeek);

        StatisticResponseDto thisWeekDto = new StatisticResponseDto(thisWeek);
        StatisticResponseDto toCompareWeekStatisticDto = new StatisticResponseDto(toCompareWeekStatistic);

        return thisWeekDto.compareWith(toCompareWeekStatisticDto);
    }

    public StatisticResponseDto getComparedMonthlyStatistic(User user, int month) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime toCompareMonth = now.minusMonths(month);

        Statistic thisWeek = getStatisticForMonth(user, now);
        Statistic toCompareWeekStatistic = getStatisticForMonth(user, toCompareMonth);

        StatisticResponseDto thisMonthDto = new StatisticResponseDto(thisWeek);
        StatisticResponseDto toCompareMonthStatisticDto = new StatisticResponseDto(toCompareWeekStatistic);

        return thisMonthDto.compareWith(toCompareMonthStatisticDto);
    }
}
