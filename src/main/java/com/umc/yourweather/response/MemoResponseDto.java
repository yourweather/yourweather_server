package com.umc.yourweather.response;

import com.umc.yourweather.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoResponseDto {

    private Status status;
    private String content;
    private String localDateTime;
    private int temperature;

    @Builder
    public MemoResponseDto(Status status, String content, String localDateTime, int temperature) {
        this.status = status;
        this.content = content;
        this.localDateTime = localDateTime;
        this.temperature = temperature;
    }
}
