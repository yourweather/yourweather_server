package com.umc.yourweather.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignupRequestDto {

    @Email
    @NotNull
    private String email;
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String platform;
}
