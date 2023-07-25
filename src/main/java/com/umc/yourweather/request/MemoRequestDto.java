package com.umc.yourweather.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.umc.yourweather.domain.Status;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoRequestDto {

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String content;

    private int temperature;

    @PrePersist
    public void setTime() {
        date = LocalDate.parse(this.datetime, DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.time = LocalTime.now();
    }

    @Builder
    public MemoRequestDto(String datetime, LocalDate date, LocalTime time, Status status,
        String content, int condition) {
        this.datetime = datetime;
        this.date = date;
        this.time = time;
        this.status = status;
        this.content = content;
        this.condition = condition;
    }
}
