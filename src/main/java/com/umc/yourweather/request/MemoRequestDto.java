package com.umc.yourweather.request;

import com.umc.yourweather.domain.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemoRequestDto {

    @Enumerated(EnumType.STRING)
    private Status status;
    @NotBlank
    private String content;

    private String localDateTime;
    private int temperature;
}
