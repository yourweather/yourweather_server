package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MemoTestRepository implements MemoRepository {
    private final List<Memo> memoListOrderByDateTime = new ArrayList<>();
    private int num;

    private void addMemo(LocalDateTime dateTime, int num) {
        List<Status> values = Arrays.asList(Status.values());
        Random random = new Random();

        for(int i = 0; i < num; i++) {
            Memo memo = Memo.builder()
                    .status(values.get(random.nextInt(values.size())))
                    .temperature(30)
                    .createdDate(dateTime.plusDays(i))
                    .build();

            memoListOrderByDateTime.add(memo);
        }
    }

    public MemoTestRepository(int num) {
        this.num = num;
        LocalDateTime dateTime = LocalDateTime.now();
        addMemo(dateTime, this.num);
    }

    @Override
    public List<Memo> findByUserAndCreatedDateBetween(User user, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        return memoListOrderByDateTime;
    }

    @Override
    public Memo save(Memo memo) {
        return null;
    }
}
