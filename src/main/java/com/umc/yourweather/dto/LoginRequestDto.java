package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
}
