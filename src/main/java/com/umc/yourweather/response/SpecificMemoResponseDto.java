package com.umc.yourweather.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@Getter
public class SpecificMemoResponseDto {
    private List<MemoReportResponseDto> memoList;
    private float proportion;
}
