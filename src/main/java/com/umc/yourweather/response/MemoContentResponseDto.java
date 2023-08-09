package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoContentResponseDto {
    private Long memoId; //메모 아이디

    private LocalDateTime creationDatetime;
    private String content;

    public MemoContentResponseDto(Memo memo) {
        this.memoId = memo.getId();
        this.creationDatetime = memo.getCreatedDateTime();
        this.content = memo.getContent();
    }
}
