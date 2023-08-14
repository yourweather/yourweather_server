package com.umc.yourweather.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddAdvertisementRequestDto {
    private String message;
    private String url;
}
