package com.umc.yourweather.response;

import com.umc.yourweather.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoUpdateResponseDto {

    private Status status; //날씨 상태 enums
    private int temperature;
    private String content;

    @Builder
    public MemoUpdateResponseDto(Status status, int temperature, String content) {
        this.status = status;
        this.temperature = temperature;
        this.content = content;
    }
}