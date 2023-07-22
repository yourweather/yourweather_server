package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Memo;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.dto.MemoRequestDto;
import com.umc.yourweather.dto.MemoResponseDto;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final WeatherRepository weatherRepository;
    private final MemoRepository memoRepository;

    public MemoResponseDto write(MemoRequestDto memoRequestDto, CustomUserDetails userDetails) {
        Weather weather = weatherRepository.findByYearAndMonthAndDay(
                memoRequestDto.getDate().getYear(),
                memoRequestDto.getDate().getMonthValue(), memoRequestDto.getDate().getDayOfMonth())
            .orElseThrow(() -> new RuntimeException("해당 날자에 날씨 객체가 존재하지 않습니다."));

        // MemoRequestDto에 넘어온 정보를 토대로 Memo 객체 생성
        Memo memo = Memo.builder()
            .weather(weather)
            .status(memoRequestDto.getStatus())
            .content(memoRequestDto.getContent())
            .condition(memoRequestDto.getCondition())
            .build();

        weather.addMemos(memo);
        memoRepository.save(memo);
        return MemoResponseDto.builder()
            .status(memo.getStatus())
            .content(memo.getContent())
            .condition(memo.getCondition())
            .build();
    }
}
