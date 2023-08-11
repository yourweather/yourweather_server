package com.umc.yourweather.request;

import com.umc.yourweather.domain.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoRequestDto {

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String content;

    private String localDateTime;
    private int temperature;

    public MemoRequestDto(Status status, String content, String localDateTime, int temperature) {
        this.status = status;
        this.content = content;
        this.localDateTime = localDateTime;
        this.temperature = temperature;
    }
}
