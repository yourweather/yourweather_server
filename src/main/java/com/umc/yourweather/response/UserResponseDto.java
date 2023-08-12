package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private String nickname;
    private String email;
    private Platform platform;

    public UserResponseDto(User user) {
        nickname = user.getNickname();
        email = user.getEmail();
        platform = user.getPlatform();
    }
}
