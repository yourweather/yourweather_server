package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoJpaRepository extends JpaRepository<Memo, Long> {

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "m.createdDateTime >= :startDateTime AND "
            + "m.createdDateTime <= :endDateTime "
            + "ORDER BY m.createdDateTime asc")
    List<Memo> findByUserAndCreatedDateBetween(
            @Param("user") User user,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "m.status = :status AND "
            + "m.createdDateTime >= :startDateTime AND "
            + "m.createdDateTime <= :endDateTime "
            + "ORDER BY m.createdDateTime asc")
    List<Memo> findSpecificMemoList(
            @Param("user") User user,
            @Param("status") Status status,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);


    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "m.weather = :weather "
            + "ORDER BY m.createdDateTime asc")
    List<Memo> findByUserAndWeatherId(
            @Param("user") User user,
            @Param("weather") Weather weatherId);

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "m.weather = :weather "
            + "ORDER BY m.temperature asc")
    List<Memo> findByWeatherId(
            @Param("weather") Weather weatherId);
}
