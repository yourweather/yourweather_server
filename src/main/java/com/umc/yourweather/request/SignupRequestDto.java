package com.umc.yourweather.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

    @Builder
    public SignupRequestDto(String email, String password, String nickname, String platform) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.platform = platform;
    }
}
