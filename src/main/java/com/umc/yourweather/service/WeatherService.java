package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Memo;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.exception.MemoNotFoundException;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.response.HomeResponseDto;
import com.umc.yourweather.request.MissedInputRequestDto;
import com.umc.yourweather.response.MissedInputResponseDto;
import com.umc.yourweather.request.WeatherRequestDto;
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
            .date(LocalDate.of(weatherRequestDto.getYear(), weatherRequestDto.getMonth(),
                weatherRequestDto.getDay()))
            .build();

        weatherRepository.save(weather);

        return "날씨 생성 완료";
    }

    public MissedInputResponseDto getMissedInputs(@Valid MissedInputRequestDto missedInputRequestDto,
        CustomUserDetails userDetails) {

        // 응답 변수 추가
        MissedInputResponseDto missedInputResponseDto = new MissedInputResponseDto();

        // 현재의 날짜 GET
        LocalDate currentDate = missedInputRequestDto.getDate();

        // 1주 전의 날짜 GET
        LocalDate oneWeekAgo = currentDate.minusWeeks(1);

        List<LocalDate> dateList = new ArrayList<>();

        LocalDate dateIterator = oneWeekAgo;

        while (!dateIterator.isAfter(currentDate)) {
            dateList.add(dateIterator);
            dateIterator.plusDays(1);
        }

        List<Weather> dates = weatherRepository.findWeatherByDateBetween(currentDate,
            oneWeekAgo);

        for (int i = 0; i < dates.size(); i++) {
            LocalDate localDate = dates.get(i).getDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();

            if (!dateList.contains(LocalDate.of(year, month, day))) {
                missedInputResponseDto.addDate(
                    LocalDate.of(year, month, day));
            }
        }
        return missedInputResponseDto;
    }

    public HomeResponseDto home(CustomUserDetails userDetails) {
        LocalDate current = LocalDate.now();

        Weather weather = weatherRepository.findByDate(current)
            .orElseThrow(() -> new WeatherNotFoundException("해당 날짜에 해당하는 날씨 객체가 존재하지 않습니다."));

        User user = userDetails.getUser();
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

    public Weather delete(LocalDate localDate, CustomUserDetails userDetails) {
        Weather weather = weatherRepository.findByDate(localDate) // User 파라미터를 추가해야 함
            .orElseThrow(() -> new WeatherNotFoundException("해당 아이디로 조회되는 날씨 객체가 존재하지 않습니다."));

        weatherRepository.delete(weather);
        return weather;
    }
}
