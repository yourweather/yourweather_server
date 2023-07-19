package com.umc.yourweather.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class WeatherRequestDto {

    private String datetime;
    private LocalDate date;

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public WeatherRequestDto(String datetime) {
        this.datetime = datetime;
        this.date = LocalDate.parse(datetime,
            java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
    }
}
