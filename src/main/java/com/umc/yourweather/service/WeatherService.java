package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.exception.MemoNotFoundException;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.response.HomeResponseDto;
//import com.umc.yourweather.request.MissedInputRequestDto;
import com.umc.yourweather.response.MissedInputResponseDto;
//import com.umc.yourweather.request.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
import com.umc.yourweather.response.WeatherResponseDto;
//import jakarta.validation.Valid;
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
    public MissedInputResponseDto getMissedInputs(
        CustomUserDetails userDetails) {

        // 응답 변수 추가
        MissedInputResponseDto missedInputResponseDto = new MissedInputResponseDto();

        // 현재의 날짜 GET
        LocalDate current = LocalDate.now();

        // 1주 전의 날짜 GET
        LocalDate oneWeekAgo = current.minusWeeks(1);
        List<LocalDate> dates = new ArrayList<>();

        LocalDate dateIterator = oneWeekAgo.plusDays(1);

        while (!dateIterator.isAfter(current)) {
            dates.add(dateIterator);
            dateIterator = dateIterator.plusDays(1);
        }

        List<Weather> weathers = weatherRepository.findWeatherByDateBetween(oneWeekAgo,
            current);

        for (Weather weather : weathers) {
            LocalDate localDate = weather.getDate();
            dates.remove(localDate);
        }

        missedInputResponseDto.setLocalDates(dates);

        return missedInputResponseDto;
    }

    public HomeResponseDto home(CustomUserDetails userDetails) {
        LocalDate current = LocalDate.now();

        Weather weather = weatherRepository.findByDate(current)
            .orElseThrow(() -> new WeatherNotFoundException("해당 날짜에 해당하는 날씨 객체가 존재하지 않습니다."));

        User user = userDetails.getUser();
        // 션: 튜닝 가능성이 있어보입니다! 나중에 튜닝해봐용
        List<Memo> memos = weather.getMemos();
        if (memos.isEmpty()) {
            throw new MemoNotFoundException("해당 날짜의 날씨에 대한 메모가 없습니다.");
        }

        Memo lastMemo = memos.get(memos.size() - 1);
        return HomeResponseDto.builder()
            .nickname(user.getNickname())
            .status(lastMemo.getStatus())
            .temperature(lastMemo.getTemperature())
            .build();

    }

    @Transactional
    public WeatherResponseDto delete(LocalDate localDate, CustomUserDetails userDetails) {
        Weather weather = weatherRepository.findByDateAndUser(localDate, userDetails.getUser()) // User 파라미터를 추가해야 함
            .orElseThrow(() -> new WeatherNotFoundException("해당 아이디로 조회되는 날씨 객체가 존재하지 않습니다."));

        WeatherResponseDto result = new WeatherResponseDto(weather);
        weatherRepository.delete(weather);

        return result;
    }
}
