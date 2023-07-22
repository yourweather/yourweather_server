package com.umc.yourweather.dto;

import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NoInputRequestDto {

    LocalDateTime time;

    @PrePersist
    public void setTime() {
        this.time = LocalDateTime.now();
    }
}
