package com.umc.yourweather.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissedInputRequestDto {

    LocalDate date;

    public MissedInputRequestDto(LocalDate date) {
        this.date = date;
    }
}
