package com.umc.yourweather.service;

import com.umc.yourweather.domain.StatisticCreator;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.response.MemoReportResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

    public List<MemoReportResponseDto> getSpecificMemoList(User user, Status status, int month) {
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.now().getYear(),
                month,
                1,
                0,
                0,
                0);
        LocalDateTime endDateTime = startDateTime.withDayOfMonth(
                startDateTime.toLocalDate().lengthOfMonth());

        List<Memo> memoList = memoRepository.findSpecificMemoList(user, status, startDateTime, endDateTime);

        return memoList.stream()
                .map(MemoReportResponseDto::new)
                .collect(Collectors.toList());
    }
}
