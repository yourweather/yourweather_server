package com.umc.yourweather.response;

import com.umc.yourweather.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private String nickname;
    private String email;

    public UserResponseDto(User user) {
        nickname = user.getNickname();
        email = user.getEmail();
    }
}
