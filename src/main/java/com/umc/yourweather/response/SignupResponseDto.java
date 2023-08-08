package com.umc.yourweather.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupResponseDto {
    private final String accessToken;
    private final String refreshToken;
}
