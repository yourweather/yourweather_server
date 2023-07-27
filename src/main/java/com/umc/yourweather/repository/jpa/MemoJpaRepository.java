package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoJpaRepository extends JpaRepository<Memo, Long> {

    @Query("SELECT m FROM Memo m "
            + "JOIN FETCH m.weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "m.createdDate >= :startDateTime AND "
            + "m.createdDate <= :endDateTime "
            + "ORDER BY m.createdDate asc")
    List<Memo> findByUserAndCreatedDateBetween(
            @Param("user") User user,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
}
