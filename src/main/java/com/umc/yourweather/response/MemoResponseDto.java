package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    @Schema(description = "날씨 값 ex.  SUNNY, CLOUDY, RAINY, LIGHTNING" , example = "SUNNY")
    private Status status;
    @Schema(description = "코멘트" , example = "덥다")
    private String content;
    @Schema(description = "일시" , example = "2023-08-09T21:07")
    private String localDateTime;
    @Schema(description = "온도" , example = "30")
    private int temperature;

    @Builder
    public MemoResponseDto(Status status, String content, String localDateTime, int temperature) {
        this.status = status;
        this.content = content;
        this.localDateTime = localDateTime;
        this.temperature = temperature;
    }

    @Builder
    public MemoResponseDto(Memo memo) {
        this.status = memo.getStatus();
        this.content = memo.getContent();
        this.localDateTime = String.valueOf(memo.getCreatedDateTime());
        this.temperature = memo.getTemperature();
    }
}
