package com.umc.yourweather.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String userPw;

    private String nickname;
    private String provider;
    private String providerId;

    private String refreshToken;

    private Role role;

    @Builder
    public User(String email,
                String userPw,
                String nickname,
                String provider,
                String providerId,
                String refreshToken,
                Role role) {
        this.email = email;
        this.userPw = userPw;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public String getUserPw() {
        return userPw;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
