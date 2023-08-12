package com.umc.yourweather.domain;

import com.umc.yourweather.domain.entity.ReIssuedToken;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReIssueTokenProvider {
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

    @Transactional
    public ReIssuedToken reissueToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElse(null);

        String newAccessToken = jwtTokenManager.createAccessToken(Objects.requireNonNull(user));
        String newRefreshToken = jwtTokenManager.createRefreshToken();
        System.out.println("refreshToken = " + refreshToken);
        System.out.println("newRefreshToken = " + newRefreshToken);

        jwtTokenManager.updateRefreshToken(user, newRefreshToken);

        return ReIssuedToken.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
