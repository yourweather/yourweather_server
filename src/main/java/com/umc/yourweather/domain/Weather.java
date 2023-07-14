package com.umc.yourweather.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long id;

    private String email; //PK

    private Condition condition; //날씨 상태 enums

    private Time time; //낮, 밤 -> day, night enums
    //enum 이름 추천 받아요..
    //enum을 클래스로 따로 빼는게 맞는가? inner class로 하는게 맞는가?

    private String temperature; //온도
    private String diary; //diary? comment?기

    @NotNull
    private String datetime; //yyyyMMDD 형식으로 받아와서, 파싱해서 넣을 예정

    private Integer year;
    private Integer month;
    private Integer day;

    LocalDate date = LocalDate.parse(datetime, java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

    @Builder
    public Weather(String email,
                   Condition condition,
                   String temperature,
                   String diary,
                   String datetime,
                   Time time) {
        this.email = email;
        this.condition = condition;
        this.temperature = temperature;
        this.diary = diary;
        this.datetime = datetime; //yyyyMMDD
        this.year =date.getYear();
        this.month =date.getMonthValue();
        this.day=date.getDayOfMonth();
        this.time = time;
    }
}
