package com.umc.yourweather.dto;

import com.umc.yourweather.domain.Status;
import lombok.Getter;

@Getter
public class MemoRequestDto {

    Status status;
    String content;
    int condition;
    int year;
    int month;
    int day;
}
