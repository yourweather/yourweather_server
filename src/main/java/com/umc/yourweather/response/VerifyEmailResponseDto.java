package com.umc.yourweather.response;

import com.umc.yourweather.domain.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VerifyEmailResponseDto {
    private boolean oauth;
    private Platform platform;
}
