package com.umc.yourweather.domain;

import java.time.LocalDate;

public class SystemDateProvider implements DateProvider {

    @Override
    public LocalDate getToday() {
        return LocalDate.now();
    }

    @Override
    public LocalDate getOneWeekAgo() {
        return LocalDate.now().minusWeeks(1).plusDays(1);
    }
}
