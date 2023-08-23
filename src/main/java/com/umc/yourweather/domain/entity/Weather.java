package com.umc.yourweather.domain.entity;

import com.umc.yourweather.domain.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Weathers")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;
    private LocalDate date;

    // 대표날씨 필드, 그 날씨의 온도
    @Enumerated(EnumType.STRING)
    private Status lastStatus;
    private int lastTemperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "weather", cascade = CascadeType.REMOVE)
    List<Memo> memos = new ArrayList<>();

    @Builder
    public Weather(LocalDate date, User user, Status lastStatus, int lastTemperature) {
        this.date = date;
        this.user = user;
        this.lastStatus = lastStatus;
        this.lastTemperature = lastTemperature;
    }

    public void update(Status lastStatus, int lastTemperature) {
//        if (lastTemperature >= this.lastTemperature) {
        this.lastStatus = lastStatus;
        this.lastTemperature = lastTemperature;
//        }
    }
}