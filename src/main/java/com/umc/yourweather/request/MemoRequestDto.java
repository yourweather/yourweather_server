package com.umc.yourweather.request;

import com.umc.yourweather.domain.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoRequestDto {

    @Enumerated(EnumType.STRING)
    private Status status;
    @NotBlank
    private String content;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private int second;
    private int temperature;
}
