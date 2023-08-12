package com.umc.yourweather.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReIssuedToken {
    private final String accessToken;
    private final String refreshToken;
}
