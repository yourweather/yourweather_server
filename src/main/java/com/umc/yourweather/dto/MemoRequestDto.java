package com.umc.yourweather.dto;

import com.umc.yourweather.domain.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemoRequestDto {

    @NotBlank
    Status status;

    @NotBlank
    String content;

    @NotBlank
    int condition;

    @NotBlank
    int year;
    @NotBlank
    int month;
    @NotBlank
    int day;
}
