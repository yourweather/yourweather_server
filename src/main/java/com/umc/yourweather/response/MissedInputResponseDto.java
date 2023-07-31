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

    public void addDate(LocalDate localDate) {
        localDates.add(localDate);
    }
}
