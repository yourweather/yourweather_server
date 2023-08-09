package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDateTime;
import java.util.*;

public class MemoTestRepository implements MemoRepository {
    private final List<Memo> memoListOrderByDateTime = new ArrayList<>();
    private final int num;
    private boolean random;

    private void addMemoByRandom(LocalDateTime dateTime, int num) {
        List<Status> values = Arrays.asList(Status.values());
        Random random = new Random();

        for(int i = 0; i < num; i++) {
            Memo memo = Memo.builder()
                    .status(values.get(random.nextInt(values.size())))
                    .temperature(30)
                    .createdDateTime(dateTime.plusDays(i))
                    .build();

            memoListOrderByDateTime.add(memo);
        }
    }

    private void addMemoByNonRandom(LocalDateTime dateTime, int num) {


    }

    public MemoTestRepository(int num, boolean random) {
        this.num = num;
        this.random = random;
        LocalDateTime dateTime = LocalDateTime.now();

        if (random)
            addMemoByRandom(dateTime, this.num);

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
