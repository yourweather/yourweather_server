package com.umc.yourweather.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String nickname;
    private String platform;

    private String refreshToken;

    private Role role;

    @Builder
    public User(String email,
            String password,
            String nickname,
            String platform,
            Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.platform = platform;
        this.refreshToken = UUID.randomUUID().toString();
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getNickname() {
        return nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
