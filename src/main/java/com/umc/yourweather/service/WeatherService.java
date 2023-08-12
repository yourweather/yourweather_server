package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.exception.MemoNotFoundException;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.response.*;
//import com.umc.yourweather.request.MissedInputRequestDto;
//import com.umc.yourweather.request.WeatherRequestDto;
import com.umc.yourweather.repository.WeatherRepository;
//import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        List<Weather> weathers = weatherRepository.findWeatherByDateBetweenAndUser(oneWeekAgo,
                current,userDetails.getUser());

        for (Weather weather : weathers) {
            LocalDate localDate = weather.getDate();
            dates.remove(localDate);
        }

        missedInputResponseDto.setLocalDates(dates);

        return missedInputResponseDto;
    }

    public HomeResponseDto home(CustomUserDetails userDetails) {
        LocalDate localDate = LocalDate.now();

        Weather weather = weatherRepository.findByDateAndUser(localDate,userDetails.getUser())
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
                .orElseThrow(() -> new WeatherNotFoundException("해당 날짜에 대한 Weather 엔티티가 존재하지 않습니다."));

        WeatherResponseDto result = new WeatherResponseDto(weather);
        weatherRepository.delete(weather);

        return result;
    }

    @Transactional
    public String checkMemoAndDelete(Long weatherId) {
        Weather weather = weatherRepository.findById(weatherId)
                .orElseThrow(
                        () -> new WeatherNotFoundException("해당 아이디로 조회되는 Weather 엔티티가 존재하지 않습니다."));

        List<Memo> memoList = weather.getMemos();
        if (memoList.size() > 0) {
            return "";
        }

        weatherRepository.delete(weather);

        return " (해당 날짜의 Memo가 전부 삭제되어 해당 날짜의 Weather 또한 같이 삭제되었습니다.)";
    }

    @Transactional(readOnly = true)
    public WeatherMonthlyResponseDto getMonthlyList(int year, int month, CustomUserDetails userDetails) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate lastDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<WeatherItemResponseDto> weatherList = weatherRepository.findByMonthAndUser(userDetails.getUser(), startDate, lastDate); // User 파라미터를 추가해야 함
        //.orElseThrow(() -> new WeatherNotFoundException("해당 아이디로 조회되는 날씨 객체가 존재하지 않습니다."));

        WeatherMonthlyResponseDto result = new WeatherMonthlyResponseDto(weatherList);
        return result;
    }
}