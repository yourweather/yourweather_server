package com.umc.yourweather.dto;

import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class NoInputRequestDto {

    LocalDate time;

    @PrePersist
    public void setTime() {
        this.time = LocalDate.now();
    }
}
