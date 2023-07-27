package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.Memo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoJpaRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
