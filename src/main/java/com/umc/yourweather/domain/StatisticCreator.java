package com.umc.yourweather.domain;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatisticCreator {
    private final MemoRepository memoRepository;

    public Statistic createWeeklyStatistic(User user, LocalDateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek().getValue();

        LocalDateTime startDateTime = dateTime.minusDays(dayOfWeek - 1);
        LocalDateTime endDateTime = dateTime.plusDays(7 - dayOfWeek);

        List<Memo> memoList = memoRepository.findByUserAndCreatedDateBetween(user, startDateTime,
                endDateTime);

        Statistic statistic = new Statistic();

        memoList.forEach(memo -> statistic.plusPoint(memo.getStatus()));

        return statistic;
    }
}
