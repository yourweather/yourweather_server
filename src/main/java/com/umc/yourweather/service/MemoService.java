package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.exception.MemoNotFoundException;
import com.umc.yourweather.exception.WeatherNotFoundException;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.WeatherRepository;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.request.MemoUpdateRequestDto;
import com.umc.yourweather.response.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemoService {

    private final WeatherRepository weatherRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public Optional<MemoResponseDto> write(MemoRequestDto memoRequestDto, CustomUserDetails userDetails) {
        LocalDateTime dateTime = LocalDateTime.parse(memoRequestDto.getLocalDateTime());

        LocalDate date = dateTime.toLocalDate();

        User user = userDetails.getUser();

        // weather 찾아보고 만약 없으면 새로 등록해줌.
        Weather weather = weatherRepository.findByDateAndUser(date, user)
                .orElseGet(() -> {
                    Weather newWeather = Weather.builder()
                            .user(user)
                            .date(date)
                            .build();

                    return weatherRepository.save(newWeather);
                });

        // 10보다 클 수는 없겠지만 확실한 쪽이 안심이 되니까,,
        if (weather.getMemos().size() >= 10) {
            return Optional.empty();
        }

        // MemoRequestDto에 넘어온 정보를 토대로 Memo 객체 생성
        Memo memo = Memo.builder()
                .weather(weather)
                .status(memoRequestDto.getStatus())
                .content(memoRequestDto.getContent())
                .temperature(memoRequestDto.getTemperature())
                .createdDateTime(dateTime)
                .build();

        memoRepository.save(memo);
        return Optional.of(
                MemoResponseDto.builder()
                        .memo(memo)
                        .weatherId(weather.getId())
                        .build());
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

    // weather의 id를 반환
    @Transactional
    public Long delete(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException("해당 메모가 없습니다. id =" + memoId));
        memoRepository.delete(memo);

        Long weatherId = memo.getWeather().getId();

        return weatherId;
    }

    @Transactional
    public MemoDailyResponseDto getDailyList(Long weatherId, CustomUserDetails userDetails) {
        Weather weather = weatherRepository.findById(weatherId)
                .orElseThrow(() -> new WeatherNotFoundException("해당하는 Weather 객체가 없습니다."));

        List<MemoItemResponseDto> memoList = memoRepository.findByUserAndWeatherId(userDetails.getUser(), weather)
                .stream()
                .map(MemoItemResponseDto::new)
                .collect(Collectors.toList()); // User 파라미터를 추가해야 함

        List<MemoContentResponseDto> memoContentList = memoRepository.findByUserAndWeatherId(userDetails.getUser(), weather)
                .stream()
                .map(MemoContentResponseDto::new)
                .collect(Collectors.toList()); // User 파라미터를 추가해야 함


        MemoDailyResponseDto result = new MemoDailyResponseDto(memoList, memoContentList);
        return result;
    }

    @Transactional(readOnly = true)
    public MemoResponseDto getOneMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoNotFoundException("해당하는 Memo 객체가 없습니다."));
        MemoResponseDto result = new MemoResponseDto(memo, memo.getWeather().getId());

        return result;
    }
}

