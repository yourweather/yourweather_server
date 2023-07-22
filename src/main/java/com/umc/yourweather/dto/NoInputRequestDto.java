package com.umc.yourweather.dto;

import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

public class NoInputRequestDto {

    LocalDateTime localDateTime;

    @PrePersist
    public void setLocalDateTime() {
        this.localDateTime = LocalDateTime.now();
    }
}
