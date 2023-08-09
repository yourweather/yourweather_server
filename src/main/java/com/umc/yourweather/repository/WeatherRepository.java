package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Weather> findByDateAndUser(LocalDate localDate, User user);

    Optional<Weather> findByDate(LocalDate localDate);

    List<WeatherItemResponseDto> findByMonthAndUser(User user, LocalDate startDate, LocalDate endDate);


    @Query("SELECT w FROM Weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "w.date >= :startDate AND "
            + "w.date <= :endDate "
            + "ORDER BY w.date asc")
    List<Weather> findByMonthAndUser(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
