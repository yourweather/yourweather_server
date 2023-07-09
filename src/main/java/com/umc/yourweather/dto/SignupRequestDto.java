package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotNull
    private String email;
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String platform;
}
