package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    @Schema(description = "닉네임" , example = "eddy")
    private String nickname;
    @Schema(description = "가입한 이메일 값" , example = "eddy@gmail.com")
    private String email;
    @Schema(description = "날씨 값 ex. YOURWEATHER, KAKAO, NAVER, GOOGLE" , example = "YOURWEATHER")
    private Platform platform;

    public UserResponseDto(User user) {
        nickname = user.getNickname();
        email = user.getEmail();
        platform = user.getPlatform();
    }
}
