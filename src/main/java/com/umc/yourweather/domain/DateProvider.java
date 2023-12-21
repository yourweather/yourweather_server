package com.umc.yourweather.domain;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DateProvider {

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public LocalDate getOneWeekAgo() {
        return LocalDate.now().minusWeeks(1).plusDays(1);
    }
}
