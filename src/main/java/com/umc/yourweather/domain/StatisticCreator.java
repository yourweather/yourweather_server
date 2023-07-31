package com.umc.yourweather.domain;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatisticCreator {

    private final MemoRepository memoRepository;

    private Statistic getStatistic(User user, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Memo> memoList = memoRepository.findByUserAndCreatedDateBetween(user, startDateTime,
                endDateTime);

        Statistic statistic = new Statistic();

        memoList.forEach(memo -> statistic.plusPoint(memo.getStatus()));

        return statistic;
    }

    public Statistic createWeeklyStatistic(User user, LocalDateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek().getValue();

        LocalDate startDate = dateTime.toLocalDate().minusDays(dayOfWeek - 1);
        LocalTime startTime = LocalTime.of(0, 0, 0);
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = dateTime.toLocalDate().plusDays(7 - dayOfWeek);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        return getStatistic(user, startDateTime, endDateTime);
    }


    public Statistic createMonthlyStatistic(User user, LocalDateTime dateTime) {
        LocalDateTime startDateTime = dateTime.withDayOfMonth(1);
        LocalDateTime endDateTime = dateTime.withDayOfMonth(
                dateTime.toLocalDate().lengthOfMonth());

        return getStatistic(user, startDateTime, endDateTime);
    }
}
