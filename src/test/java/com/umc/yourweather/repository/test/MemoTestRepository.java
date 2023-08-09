package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.Proportion;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
import java.util.*;

public class MemoTestRepository implements MemoRepository {
    private final List<Memo> memoListOrderByDateTime = new ArrayList<>();

    private void addMemoByRandom(LocalDateTime dateTime, int num) {
        List<Status> values = Arrays.asList(Status.values());
        Random random = new Random();

        for(int i = 0; i < num; i++) {
            Memo memo = Memo.builder()
                    .status(values.get(random.nextInt(values.size())))
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            memoListOrderByDateTime.add(memo);
        }
    }

    private void addMemoByNonRandom(LocalDateTime dateTime, Proportion proportion) {
        for(int i = 0; i < proportion.sunny; i++) {
            Memo memo = Memo.builder()
                    .status(Status.SUNNY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            memoListOrderByDateTime.add(memo);
        }

        for(int i = 0; i < proportion.cloudy; i++) {
            Memo memo = Memo.builder()
                    .status(Status.CLOUDY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            memoListOrderByDateTime.add(memo);
        }

        for(int i = 0; i < proportion.rainy; i++) {
            Memo memo = Memo.builder()
                    .status(Status.RAINY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            memoListOrderByDateTime.add(memo);
        }

        for(int i = 0; i < proportion.lightning; i++) {
            Memo memo = Memo.builder()
                    .status(Status.LIGHTNING)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            memoListOrderByDateTime.add(memo);
        }
    }

    public MemoTestRepository(int num) {
        LocalDateTime dateTime = LocalDateTime.now();

        addMemoByRandom(dateTime, num);
    }

    public MemoTestRepository(Proportion proportion) {
        LocalDateTime dateTime = LocalDateTime.now();

        addMemoByNonRandom(dateTime, proportion);
    }

    @Override
    public List<Memo> findByUserAndCreatedDateBetween(User user, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return memoListOrderByDateTime;
    }

    @Override
    public List<Memo> findSpecificMemoList(User user, Status status, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return memoListOrderByDateTime;
    }

    @Override
    public Memo save(Memo memo) {
        return memo;
    }

    @Override
    public Optional<Memo> findById(Long memoId) {
        return null;
    }

    @Override
    public void delete(Memo memo) {

    }
}
