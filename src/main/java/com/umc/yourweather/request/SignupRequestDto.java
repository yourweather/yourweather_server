package com.umc.yourweather.request;

import com.umc.yourweather.domain.enums.Platform;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignupRequestDto {

    @Email
    @NotNull
    private String email;
    private String password;
    @NotBlank
    private String nickname;
    private Platform platform;

    @Builder
    public SignupRequestDto(String email, String password, String nickname, Platform platform) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.platform = platform;
    }
}
