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

    @Builder
    public User(String email, String userPw, String nickname, String provider, String providerId) {
        this.email = email;
        this.userPw = userPw;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getEmail() {
        return email;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
