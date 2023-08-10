package com.umc.yourweather.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeNicknameRequestDto {
    @NotBlank
    private String nickname;
}
