package com.umc.yourweather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangePasswordDto {

    @NotNull
    String password;
}
