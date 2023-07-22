package com.umc.yourweather.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoInputResponseDto {

    List<LocalDate> localDates = new ArrayList<>();

    public void addDate(LocalDate localDate) {
        localDates.add(localDate);
    }
}
