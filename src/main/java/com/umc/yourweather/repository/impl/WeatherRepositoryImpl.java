package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WeatherRepositoryImpl {
//        implements WeatherRepository {
    private final List<Weather> weathers = new ArrayList<>();
//
//    @Override
//    public void writeWeather(Weather weather) throws Exception {
//        boardMapper.insertBoard(board);
//    }

//    @Override
//    public List<Weather> findByDate(LocalDate date) {
//        return weathers.stream()
//                .filter(entry -> entry.getDate().equals(date))
//                .toList();
//    }
//
//    @Override
//    public List<Weather> findByMonth(int month, int year) {
//        return weathers.stream()
//                .filter(entry -> entry.getDate().getMonthValue() == month && entry.getDate().getYear() == year)
//                .toList();
//    }
}
