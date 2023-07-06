package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.repository.jpa.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email + ": 해당 email 정보를 가지고 있는 사용자가 없습니다."));
    }

    @Override
    public User findByRefreshToken(String refreshToken) {
        return userJpaRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException(refreshToken + ": 해당 Refresh Token을 가지고 있는 사용자가 없습니다."));
    }
}
