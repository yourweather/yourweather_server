package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    @NotBlank
    private String nickname;
    @NotBlank
    private String email;
}
