package com.umc.yourweather.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordResponseDto {
    private boolean success;
    private String message;
    private boolean occurredByDB;
    private boolean occurredByPassword;
}
