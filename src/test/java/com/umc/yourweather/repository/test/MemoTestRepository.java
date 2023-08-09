package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.Proportion;
import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MemoTestRepository implements MemoRepository {
    private final List<Memo> memoListOrderByDateTime = new ArrayList<>(); // 현재 주 현재 달 리스트
    private final List<Memo> preMonthMemoList = new ArrayList<>();

    /**
     * preWeekMemoList 요소 채우는 기준 :
     * 이번 주 -> 이전 주
     * sunny 갯수 -> lightning 갯수
     * cloudy 갯수 -> rainy 갯수
     * rainy 갯수 -> cloudy 갯수
     * lightning 갯수 -> sunny 갯수
     */
    private final List<Memo> preWeekMemoList = new ArrayList<>();
    private final List<Memo> sunnyList = new ArrayList<>();
    private final List<Memo> cloudyList = new ArrayList<>();
    private final List<Memo> rainyList = new ArrayList<>();
    private final List<Memo> lightningList = new ArrayList<>();

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

            Memo preWeekMemo = Memo.builder()
                    .status(Status.LIGHTNING)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            sunnyList.add(memo);
            memoListOrderByDateTime.add(memo);
            preWeekMemoList.add(preWeekMemo);
            preMonthMemoList.add(preWeekMemo);
        }

        for(int i = 0; i < proportion.cloudy; i++) {
            Memo memo = Memo.builder()
                    .status(Status.CLOUDY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            Memo preWeekMemo = Memo.builder()
                    .status(Status.RAINY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            cloudyList.add(memo);
            memoListOrderByDateTime.add(memo);
            preWeekMemoList.add(preWeekMemo);
            preMonthMemoList.add(preWeekMemo);
        }

        for(int i = 0; i < proportion.rainy; i++) {
            Memo memo = Memo.builder()
                    .status(Status.RAINY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            Memo preWeekMemo = Memo.builder()
                    .status(Status.CLOUDY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            rainyList.add(memo);
            memoListOrderByDateTime.add(memo);
            preWeekMemoList.add(preWeekMemo);
            preMonthMemoList.add(preWeekMemo);
        }

        for(int i = 0; i < proportion.lightning; i++) {
            Memo memo = Memo.builder()
                    .status(Status.LIGHTNING)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            Memo preWeekMemo = Memo.builder()
                    .status(Status.SUNNY)
                    .temperature(30)
                    .createdDateTime(dateTime)
                    .build();

            lightningList.add(memo);
            memoListOrderByDateTime.add(memo);
            preWeekMemoList.add(preWeekMemo);
            preMonthMemoList.add(preWeekMemo);
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
        LocalDate now = LocalDate.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        LocalDate startDate = now.minusDays(dayOfWeek - 1);

        System.out.println("startDateTime = " + startDateTime);
        System.out.println("startDate = " + startDate);
        System.out.println("startDateTime.toLocalDate()==startDate = " + (Objects.equals(startDateTime.toLocalDate(), startDate)));
        System.out.println("startDateTime.toLocalDate()==startDateTime.withDayOfMonth(1).toLocalDate() = " + (Objects.equals(startDateTime.toLocalDate(), startDateTime.withDayOfMonth(1).toLocalDate())));
        System.out.println("startDateTime.toLocalDate()==startDateTime.withDayOfMonth(1).minusMonths(1).toLocalDate() = " + (Objects.equals(startDateTime.toLocalDate(), startDateTime.withDayOfMonth(1).minusMonths(1).toLocalDate())));

        if(Objects.equals(startDateTime.toLocalDate(), startDate))
            return memoListOrderByDateTime;
        else if (Objects.equals(startDateTime.toLocalDate(), startDate.withDayOfMonth(1)))
            return memoListOrderByDateTime;
        else if (Objects.equals(startDateTime.toLocalDate(), startDate.withDayOfMonth(1)))
            return preMonthMemoList;
        else
            return preWeekMemoList;
    }

    @Override
    public List<Memo> findSpecificMemoList(User user, Status status, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        List<Memo> returnList = new ArrayList<>();
        switch(status) {
            case SUNNY -> returnList = sunnyList;
            case CLOUDY -> returnList = cloudyList;
            case RAINY -> returnList = rainyList;
            case LIGHTNING -> returnList = lightningList;
        }
        return returnList;
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
