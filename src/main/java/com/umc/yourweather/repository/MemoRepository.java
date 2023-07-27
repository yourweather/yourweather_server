package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.domain.entity.User;
import java.time.LocalDateTime;
import java.util.List;

public interface MemoRepository {
    List<Memo> findByUserAndCreatedDateBetween(User user, LocalDateTime startDateTime,
            LocalDateTime endDateTime);

    Memo save(Memo memo);
}
