package com.umc.yourweather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MypageResponseDto {

    private String nickname;
    private String email;
}
