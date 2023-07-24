package com.umc.yourweather.dto;

import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class NoInputRequestDto {

    LocalDate date;

    @PrePersist
    public void setDate() {
        this.date = LocalDate.now();
    }
}
