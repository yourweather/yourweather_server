package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    User save(User user);

    void deleteExpiredUser();
}
