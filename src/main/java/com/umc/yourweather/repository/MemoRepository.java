package com.umc.yourweather.repository;

import com.umc.yourweather.domain.Memo;
import java.time.LocalDateTime;
import java.util.List;

public interface MemoRepository {
    List<Memo> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    Memo save(Memo memo);
}
