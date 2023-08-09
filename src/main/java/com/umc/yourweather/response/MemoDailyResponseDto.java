package com.umc.yourweather.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
public class MemoDailyResponseDto {
    List<MemoItemResponseDto> memoList;
    List<MemoContentResponseDto> memoContentList;

    public MemoDailyResponseDto(List<MemoItemResponseDto> memoList, List<MemoContentResponseDto> memoContentList) {
        this.memoList = memoList;
        this.memoContentList = memoContentList;
    }
}
