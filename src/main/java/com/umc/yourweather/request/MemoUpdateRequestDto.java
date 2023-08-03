package com.umc.yourweather.request;

import com.umc.yourweather.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoUpdateRequestDto {

    private Status status; //날씨 상태 enums
    private int temperature;
    private String content;

    @Builder
    public MemoUpdateRequestDto(Status status, int temperature, String content) {
        this.status = status;
        this.temperature = temperature;
        this.content = content;
    }
}