package com.umc.yourweather.domain;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "weather", cascade = CascadeType.REMOVE)
    List<Memo> memos = new ArrayList<>();

    @Builder
    public Weather(LocalDate date, User user) {
        this.date = date;
        this.user = user;
    }

    public boolean isSameDate(LocalDate dateToCheck) {
        return date.equals(dateToCheck);
    }
}
