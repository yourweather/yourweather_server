package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.Memo;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.domain.Weather;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.response.MemoResponseDto;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.WeatherRepository;

import java.sql.SQLException;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final WeatherRepository weatherRepository;
    private final MemoRepository memoRepository;

    public MemoResponseDto write(MemoRequestDto memoRequestDto, CustomUserDetails userDetails) {
        LocalDate date = LocalDate.of(memoRequestDto.getYear(), memoRequestDto.getMonth(),
                memoRequestDto.getDay());
        Weather weather = weatherRepository.findByDate(LocalDate.of(
                        date.getYear(),
                        date.getMonthValue(), date.getDayOfMonth()))
                .orElseThrow(() -> new RuntimeException("해당 날자에 날씨 객체가 존재하지 않습니다."));

        // MemoRequestDto에 넘어온 정보를 토대로 Memo 객체 생성
        Memo memo = Memo.builder()
                .weather(weather)
                .status(memoRequestDto.getStatus())
                .content(memoRequestDto.getContent())
                .temperature(memoRequestDto.getTemperature())
                .build();

        memoRepository.save(memo);
        return MemoResponseDto.builder()
                .status(memo.getStatus())
                .content(memo.getContent())
                .temperature(memo.getTemperature())
                .build();
    }

    @Transactional //<-@@
    public void delete(Long id) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다. id=" + id));
        memoRepository.delete(memo); //JpaRepository에서 제공하고 있는 delete 메소드 사용
    }
}

