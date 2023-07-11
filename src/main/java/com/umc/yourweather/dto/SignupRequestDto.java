package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignupRequestDto {

    @NotNull
    private String email;
    private String password;
    @NotNull
    private String nickname;
    @NotNull
    private String platform;
}
