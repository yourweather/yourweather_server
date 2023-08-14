package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM User u "
            + "WHERE u.unActivatedDate <= :cutoffDate")
    void deleteExpiredUser(@Param("cutoffDate") LocalDate cutoffDate);
}
