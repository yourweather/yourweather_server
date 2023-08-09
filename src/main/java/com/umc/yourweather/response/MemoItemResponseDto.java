package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoItemResponseDto {

    private Long memoId; //메모 아이디

    private LocalDateTime creationDatetime;
    private Status status;
    private int temperature;
//    private String content;

    public MemoItemResponseDto(Memo memo) {
        this.memoId = memo.getId();
        this.creationDatetime = memo.getCreatedDateTime();
        this.status = memo.getStatus();
        this.temperature = memo.getTemperature();
//        this.content = memo.getContent();
    }
}
