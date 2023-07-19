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
    private int year;
    private int month;
    private int day;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @OneToMany(mappedBy = "weather")
    List<Memo> memos = new LinkedList<>();

    @Builder
    public Weather(int year, int month, int day, User user, List<Memo> memos) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.user = user;
        this.memos = memos;
    }
}
