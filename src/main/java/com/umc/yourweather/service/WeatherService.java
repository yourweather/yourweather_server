package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.HomeResponseDto;
import com.umc.yourweather.dto.NoInputRequestDto;
import com.umc.yourweather.dto.NoInputResponseDto;
import com.umc.yourweather.dto.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Transactional
    public String create(WeatherRequestDto weatherRequestDto, CustomUserDetails userDetails) {
        Weather weather = Weather.builder()
            .user(userDetails.getUser())
            .year(weatherRequestDto.getYear())
            .month(weatherRequestDto.getMonth())
            .day(weatherRequestDto.getDay())
            .build();

        weatherRepository.save(weather);

        return "날씨 생성 완료";
    }

    public NoInputResponseDto getNoInputs(@Valid NoInputRequestDto noInputRequestDto,
        CustomUserDetails userDetails) {

        // 응답 변수 추가
        NoInputResponseDto noInputResponseDto = new NoInputResponseDto();

        // 현재의 날짜 GET
        LocalDate currentDate = noInputRequestDto.getDate();

        // 1주 전의 날짜 GET
        LocalDate oneWeekAgo = currentDate.minusWeeks(1);

        List<LocalDate> dateList = new ArrayList<>();

        LocalDate dateIterator = oneWeekAgo;

        while (!dateIterator.isAfter(currentDate)) {
            dateList.add(dateIterator);
            dateIterator.plusDays(1);
        }

        List<Weather> dates = weatherRepository.findWeatherByDateTimeBetween(currentDate,
            oneWeekAgo);

        for (int i = 0; i < dates.size(); i++) {
            if (!dateList.contains(LocalDate.of(dates.get(i).getYear(), dates.get(i).getMonth(),
                dates.get(i).getDay()))) {
                noInputResponseDto.addDate(
                    LocalDate.of(dates.get(i).getYear(), dates.get(i).getMonth(),
                        dates.get(i).getDay()));
            }
        }
        return noInputResponseDto;
    }

    public HomeResponseDto home(CustomUserDetails userDetails) {
        LocalDate current = LocalDate.now();

        Weather weather = weatherRepository.findByYearAndMonthAndDay(current.getYear(),
                current.getMonthValue(), current.getDayOfMonth())
            .orElseThrow(() -> new RuntimeException("해당 날짜에 해당하는 날씨 객체가 없습니다."));

        User user = userDetails.getUser();
        return HomeResponseDto.builder()
            .nickname(user.getNickname())
            .status(weather.getMemos().get(weather.getMemos().size()).getStatus())
            .condition(weather.getMemos().get(weather.getMemos().size()).getCondition())
            .build();
    }
}
