package com.umc.yourweather.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;
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
    @NotBlank
    private String datetime; //yyyyMMDD 형식으로 받아와서, 파싱해서 넣을 예정
    private Integer year;
    private Integer month;
    private Integer day;

    @OneToMany(mappedBy = "weather")
    List<Memo> memos = new LinkedList<>();

    LocalDate date = LocalDate.parse(datetime, java.time.format.DateTimeFormatter.BASIC_ISO_DATE);

    @Builder
    public Weather(String email,
        String datetime) {
        this.email = email;
        this.datetime = datetime; //yyyyMMDD
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
    }
}
