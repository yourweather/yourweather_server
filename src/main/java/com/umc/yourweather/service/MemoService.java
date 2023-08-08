package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.request.MemoUpdateRequestDto;
import com.umc.yourweather.request.WeatherUpdateRequestDto;
import com.umc.yourweather.response.MemoResponseDto;
import com.umc.yourweather.repository.WeatherRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.umc.yourweather.response.MemoUpdateResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final WeatherRepository weatherRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDto write(MemoRequestDto memoRequestDto, CustomUserDetails userDetails) {
        LocalDateTime dateTime = LocalDateTime.parse(memoRequestDto.getLocalDateTime());

        LocalDate date = dateTime.toLocalDate();

        User user = userDetails.getUser();

        // weather 찾아보고 만약 없으면 새로 등록해줌.
        // 새로 등록할 때에 최신 날씨, 최신 온도도 같이 필드에 추가
        Weather weather = weatherRepository.findByDateAndUser(date, user)
                .orElseGet(() -> {
                    Weather newWeather = Weather.builder()
                            .user(user)
                            .date(date)
                            .lastStatus(memoRequestDto.getStatus())
                            .lastTemperature(memoRequestDto.getTemperature())
                            .build();

                    return weatherRepository.save(newWeather);
                });

        //처음에 last에 대한 정보가 생기고 바로 update를 호출하는게 조금 마음에 걸리긴하지만.. 일단 패스~
        weather.update(memoRequestDto.getStatus(), memoRequestDto.getTemperature());
        Weather existWeather = Weather.builder()
                .lastStatus(memoRequestDto.getStatus())
                .lastTemperature(memoRequestDto.getTemperature())
                .build();


        // MemoRequestDto에 넘어온 정보를 토대로 Memo 객체 생성
        Memo memo = Memo.builder()
                .weather(weather)
                .status(memoRequestDto.getStatus())
                .content(memoRequestDto.getContent())
                .temperature(memoRequestDto.getTemperature())
                .createdDateTime(dateTime)
                .build();

        memoRepository.save(memo);
        return MemoResponseDto.builder()
                .status(memo.getStatus())
                .content(memo.getContent())
                .localDateTime(memo.getCreatedDateTime().toString())
                .temperature(memo.getTemperature())
                .build();
    }

    @Transactional
    public MemoUpdateResponseDto update(Long memoId, MemoUpdateRequestDto requestDto) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메모가 없습니다. id =" + memoId));
        memo.update(requestDto.getStatus(), requestDto.getTemperature(), requestDto.getContent());

        return MemoUpdateResponseDto.builder()
                .status(memo.getStatus())
                .content(memo.getContent())
                .temperature(memo.getTemperature())
                .build();
    }

    @Transactional
    public void delete(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("해당 메모가 없습니다. id =" + memoId));

        memoRepository.delete(memo);
    }
}

