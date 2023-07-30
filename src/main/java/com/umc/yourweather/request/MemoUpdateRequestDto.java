package com.umc.yourweather.request;

import com.umc.yourweather.domain.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoUpdateRequestDto {

    private Status status; //날씨 상태 enums
    private LocalDateTime createdTime;
    private int temperature;
    private String content;

    @Builder
    public MemoUpdateRequestDto(Status status, LocalDateTime localDateTime, int temperature, String content) {
        this.status = status;
        this.createdTime = localDateTime;
        this.temperature = temperature;
        this.content = content;
    }
}