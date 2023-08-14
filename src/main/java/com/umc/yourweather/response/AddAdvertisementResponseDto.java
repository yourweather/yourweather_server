package com.umc.yourweather.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddAdvertisementResponseDto {
    private String summary;
    private String url;
}
