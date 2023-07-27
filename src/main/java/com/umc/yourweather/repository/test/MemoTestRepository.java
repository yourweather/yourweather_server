package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MemoTestRepository implements MemoRepository {
    private final List<Memo> memoListOrderByDateTime = new ArrayList<>();

    public MemoTestRepository() {
        List<Status> values = Arrays.asList(Status.values());
        Random random = new Random();
        for(int i = 0; i < 7; i++) {
            Memo memo = Memo.builder()
                    .status(values.get(random.nextInt(values.size())))
                    .temperature(30)
                    .build();

            memoListOrderByDateTime.add(memo);
        }
    }

    @Override
    public List<Memo> findByCreatedDateBetween(LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return memoListOrderByDateTime;
    }

    @Override
    public Memo save(Memo memo) {
        return null;
    }
}
