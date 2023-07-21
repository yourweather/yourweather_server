package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignupRequestDto {

    @NotBlank
    private String email;
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String platform;
}
