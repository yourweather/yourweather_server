package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.jpa.MemoJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemoRepositoryImpl implements MemoRepository {

    private final MemoJpaRepository memoJpaRepository;

    @Override
    public List<Memo> findByCreatedDateBetween(LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return memoJpaRepository.findByCreatedDateBetween(startDateTime, endDateTime);
    }

    @Override
    public Memo save(Memo memo) {
        return memoJpaRepository.save(memo);
    }
}
