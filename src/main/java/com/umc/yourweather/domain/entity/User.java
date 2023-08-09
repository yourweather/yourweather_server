package com.umc.yourweather.domain.entity;

import com.umc.yourweather.domain.enums.Role;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;

    private String nickname;
    private String platform;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActivate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<Weather> weathers = new ArrayList<>();

    @Builder
    public User(String email,
                String password,
                String nickname,
                String platform,
                Role role,
                boolean isActivate) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.platform = platform;
        this.refreshToken = UUID.randomUUID().toString();
        this.role = role;
        this.isActivate = isActivate;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void unActivate() {
        this.isActivate = false;
    }
}