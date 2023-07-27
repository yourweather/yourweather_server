package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.jpa.MemoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemoRepositoryImpl implements MemoRepository {

    private final MemoJpaRepository memoJpaRepository;

    @Override
    public List<Memo> findByUserAndCreatedDateBetween(User user, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        List<Memo> memoList = memoJpaRepository.findByUserAndCreatedDateBetween(
                user, startDateTime, endDateTime);

        if(memoList.size() > 0) {
            return memoList;
        }

        throw new EntityNotFoundException(String.format("%s의 통계 데이터 조회 실패: 해당 기간 동안의 기록이 없습니다.", user.getEmail()));
    }

    @Override
    public Memo save(Memo memo) {
        return memoJpaRepository.save(memo);
    }
}
