package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherRequestDto {

    @NotBlank
    private String datetime; //yyyyMMDD 형식으로 받아와서, 파싱해서 넣을 예정

    LocalDate date = LocalDate.parse(datetime, java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }
}
