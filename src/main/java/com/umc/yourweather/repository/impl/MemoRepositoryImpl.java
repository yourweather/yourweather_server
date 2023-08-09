package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.jpa.MemoJpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.umc.yourweather.response.MemoItemResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemoRepositoryImpl implements MemoRepository {

    private final MemoJpaRepository memoJpaRepository;

    public List<Memo> findByUserAndCreatedDateBetween(User user, LocalDateTime startDateTime,
                                                      LocalDateTime endDateTime) {
        List<Memo> memoList = memoJpaRepository.findByUserAndCreatedDateBetween(
                user, startDateTime, endDateTime);

        if (memoList.size() == 0) {
            log.error(String.format("%s의 통계 데이터 조회 실패: 해당 기간 동안의 기록이 없습니다.", user.getEmail()));
        }

        return memoList;
    }

    public List<Memo> findSpecificMemoList(User user, Status status, LocalDateTime startDateTime,
                                           LocalDateTime endDateTime) {
        List<Memo> memoList = memoJpaRepository.findSpecificMemoList(user, status,
                startDateTime, endDateTime);

        if (memoList.size() == 0) {
            log.error(String.format("%s의 월간 특정 날씨 일자 조회 실패: status나 기간을 제대로 입력했는지 확인해주세요.", user.getEmail()));
        }

        return memoList;
    }

    public Memo save(Memo memo) {
        return memoJpaRepository.save(memo);
    }

    public Optional<Memo> findById(Long id) {
        return memoJpaRepository.findById(id);
    }

    public void delete(Memo memo) {
        memoJpaRepository.delete(memo);
    }

    public List<Memo> findByUserAndWeatherId(User user, Weather weather) {
        return memoJpaRepository.findByUserAndWeatherId(user,weather);
    }

//    public List<MemoItemResponseDto> findByDateAndUser(User user, LocalDate localDate) {
//        return memoJpaRepository.findByDateAndUser(user,localDate);
//    }
}
