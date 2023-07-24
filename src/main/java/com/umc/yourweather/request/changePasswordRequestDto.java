package com.umc.yourweather.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class changePasswordRequestDto {

    @NotBlank
    String password;
}
