package com.umc.yourweather.domain.entity;

import com.umc.yourweather.domain.enums.Platform;
import com.umc.yourweather.domain.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActivate;
    private LocalDateTime unActivatedDateTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<Weather> weathers = new ArrayList<>();

    @Builder
    public User(String email,
                String password,
                String nickname,
                Platform platform,
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

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    @PrePersist
    public void setUnActivatedDateTime() {
        this.unActivatedDateTime = null;
    }

    public void unActivate() {
        this.isActivate = false;
        this.unActivatedDateTime = LocalDateTime.now();
    }
}