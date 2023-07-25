package com.umc.yourweather.response;

import com.umc.yourweather.domain.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoResponseDto {

    private Status status;
    private String content;
    private int condition;

    @Builder
    public MemoResponseDto(Status status, String content, int condition) {
        this.status = status;
        this.content = content;
        this.condition = condition;
    }
}
