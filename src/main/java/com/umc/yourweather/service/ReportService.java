package com.umc.yourweather.service;

import com.umc.yourweather.domain.StatisticCreator;
import com.umc.yourweather.domain.Statistic;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
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
}
