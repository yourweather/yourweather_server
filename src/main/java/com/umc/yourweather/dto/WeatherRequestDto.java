package com.umc.yourweather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;

@Data
public class WeatherRequestDto {

    @JsonFormat(pattern = "yyyyMMdd")
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

    @PrePersist
    public void setDate(String datetime) {
        date = LocalDate.parse(this.datetime, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Builder
    public WeatherRequestDto(String datetime) {
        this.datetime = datetime;
    }
}
