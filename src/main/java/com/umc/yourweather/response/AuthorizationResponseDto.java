package com.umc.yourweather.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthorizationResponseDto {
    private final String accessToken;
    private final String refreshToken;
}
