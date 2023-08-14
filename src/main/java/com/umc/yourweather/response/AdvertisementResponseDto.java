package com.umc.yourweather.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertisementResponseDto {
    private String company;
    private String adId;
    private String summary;
    private String message;
    private String url;
}
