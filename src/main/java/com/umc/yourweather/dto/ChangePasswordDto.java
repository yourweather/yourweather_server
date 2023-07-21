package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordDto {

    @NotBlank
    String password;
}
