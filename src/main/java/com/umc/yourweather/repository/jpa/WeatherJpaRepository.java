package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.entity.Weather;
import com.umc.yourweather.response.WeatherItemResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherJpaRepository extends JpaRepository<Weather, Long> {

    List<Weather> findWeatherByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Weather> findByDateAndUser(LocalDate localDate, User user);

    Optional<Weather> findByDate(LocalDate localDate);


    @Query("SELECT w FROM Weather w "
            + "JOIN FETCH w.user u "
            + "WHERE "
            + "u = :user AND "
            + "w.date >= :startDate AND "
            + "w.date <= :endDate "
            + "ORDER BY w.date asc")
    List<WeatherItemResponseDto> findByMonthAndUser(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<Weather> findWeatherByDateBetweenAndUser(LocalDate startDate, LocalDate endDate, User user);
}
