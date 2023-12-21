package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public record UserResponseDto(@Schema(description = "닉네임", example = "eddy") String nickname,
	              @Schema(description = "가입한 이메일 값", example = "eddy@gmail.com") String email,
	              @Schema(description = "날씨 값 ex. YOURWEATHER, KAKAO, NAVER, GOOGLE", example = "YOURWEATHER")
	              Platform platform) {


    private UserResponseDto(String nickname, String email, Platform platform) {
        this.nickname = nickname;
        this.email = email;
        this.platform = platform;
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getNickname(), user.getEmail(), user.getPlatform());
    }
}
