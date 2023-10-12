package com.umc.yourweather.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {

    @NotBlank
    private String email;
}
