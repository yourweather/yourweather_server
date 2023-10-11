package com.umc.yourweather.domain;

import java.time.LocalDate;

public interface DateProvider {
    public LocalDate getToday();
    public LocalDate getOneWeekAgo();
}
