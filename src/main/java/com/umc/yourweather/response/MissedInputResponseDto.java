package com.umc.yourweather.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissedInputResponseDto {

    List<LocalDate> localDates = new ArrayList<>();

    public MissedInputResponseDto(List<LocalDate> localDates) {
        this.localDates = localDates;
    }
}
