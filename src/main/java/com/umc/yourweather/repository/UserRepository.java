package com.umc.yourweather.repository;

import com.umc.yourweather.domain.User;

import java.util.Optional;

public interface UserRepository {
    User findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
}
