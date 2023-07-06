package com.umc.yourweather.repository;

import com.umc.yourweather.domain.User;

public interface UserRepository {
    User findByEmail(String email);
    User findByRefreshToken(String refreshToken);
}
