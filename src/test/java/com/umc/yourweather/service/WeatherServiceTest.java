//package com.umc.yourweather.service;
//
//import com.umc.yourweather.domain.entity.*;
//import com.umc.yourweather.repository.WeatherRepository;
//import com.umc.yourweather.response.MissedInputResponseDto;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@SpringBootTest
//class WeatherServiceTest {
//
//    @Autowired
//    WeatherService weatherService;
//
//    @Autowired
//    WeatherRepository weatherRepository;
//
//    @BeforeEach
//    void setup() {
//        Weather weather1 = Weather.builder()
//            .date(LocalDate.of(2023,8,2))
//            .build();
//
//        Weather weather2 = Weather.builder()
//            .date(LocalDate.of(2023,8,1))
//            .build();
//
//        Weather weather3 = Weather.builder()
//            .date(LocalDate.of(2023,7,31))
//            .build();
//
//        weatherRepository.save(weather1);
//        weatherRepository.save(weather2);
//        weatherRepository.save(weather3);
//    }
//
//    @Test
//    @DisplayName("WeatherRepository 조회")
//    public void findWeather() {
//        // given
//        Weather weather = Weather.builder()
//            .date(LocalDate.of(2023, 7, 23))
//            .build();
//
//        User user = User.builder()
//            .build();
//
//        weatherRepository.save(weather);
//
//        // when
//        Weather findWeather = weatherRepository.findByDateAndUser(LocalDate.of(2023, 7, 23), user)
//            .orElseThrow(() -> new NoSuchElementException("Not Found Weather"));
//
//        LocalDate localDate = findWeather.getDate();
//        // then
//        Assertions.assertEquals(localDate.getYear(), 2023);
//        Assertions.assertEquals(localDate.getMonthValue(), 7);
//        Assertions.assertEquals(localDate.getDayOfMonth(), 23);
//    }
//
//    @Test
//    @DisplayName("기간 별 WeatherRepository 조회")
//    public void findWeatherBetweenDate(){
//        // given
//        LocalDate start = LocalDate.of(2023,7,25);
//        LocalDate end = LocalDate.of(2023,8,3);
//
//        // when
//        List<Weather> weathers = weatherRepository.findWeatherByDateBetween(start, end);
//
//        // then
//        Assertions.assertEquals(weathers.size(), 3);
//    }
//
//    @Test
//    @DisplayName("미 입력 날자 조회")
//    public void MissedInputs() {
//        // 응답 변수 추가
//        MissedInputResponseDto response = new MissedInputResponseDto();
//
//        // 현재의 날짜 GET
//        LocalDate current = LocalDate.now();
//
//        // 1주 전의 날짜 GET
//        LocalDate oneWeekAgo = current.minusWeeks(1);
//        List<LocalDate> dates = new ArrayList<>();
//
//        LocalDate dateIterator = oneWeekAgo.plusDays(1);
//
//        while (!dateIterator.isAfter(current)) {
//            dates.add(dateIterator);
//            dateIterator = dateIterator.plusDays(1);
//        }
//
//        List<Weather> weathers = weatherRepository.findWeatherByDateBetween(oneWeekAgo,
//            current);
//
//        for (Weather weather : weathers) {
//            LocalDate localDate = weather.getDate();
//            dates.remove(localDate);
//        }
//
//        response.setLocalDates(dates);
//        Assertions.assertEquals(response.getLocalDates().size(), 4);
//    }
//}