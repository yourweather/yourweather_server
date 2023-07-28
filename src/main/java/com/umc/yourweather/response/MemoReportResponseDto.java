package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.Memo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class MemoReportResponseDto {
    private Long memoId;
    private LocalDateTime dateTime;

    public MemoReportResponseDto(Memo entity) {
        memoId = entity.getId();
        dateTime = entity.getCreatedDateTime();
    }
}
