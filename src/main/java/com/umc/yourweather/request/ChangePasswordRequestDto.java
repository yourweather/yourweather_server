package com.umc.yourweather.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ChangePasswordRequestDto {

    @NotBlank
    String password;

    public ChangePasswordRequestDto(String password) {
        this.password = password;
    }
}
