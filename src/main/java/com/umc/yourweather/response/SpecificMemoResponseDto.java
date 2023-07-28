package com.umc.yourweather.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpecificMemoResponseDto {
    private List<MemoReportResponseDto> memoList;
    private float proportion;
}
