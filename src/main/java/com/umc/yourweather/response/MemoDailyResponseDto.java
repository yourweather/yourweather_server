package com.umc.yourweather.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
public class MemoDailyResponseDto {
    List<MemoItemResponseDto> memoList;

}
