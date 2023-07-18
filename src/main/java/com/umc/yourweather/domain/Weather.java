package com.umc.yourweather.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;

    private String email; //PK

    private Status status; //날씨 상태 enums

    private Double temperature; //온도
    private String diary; //diary? comment?기

    @NotBlank
    private String datetime; //yyyyMMDD 형식으로 받아와서, 파싱해서 넣을 예정

    private Integer year;
    private Integer month;
    private Integer day;

    LocalDate date = LocalDate.parse(datetime, java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

    @Builder
    public Weather(String email,
        Status status,
        Double temperature,
        String diary,
        String datetime) {

        this.email = email;
        this.status = status;
        this.temperature = temperature;
        this.diary = diary;
        this.datetime = datetime; //yyyyMMDD
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
    }
}
