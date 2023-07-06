package com.umc.yourweather.jwt.filter;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // login 요청이 들어오면 필터에서 토큰 검증 건너뜀.
    private static final String NO_CHECK_URI = "/login";

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtTokenManager.extractRefreshToken(request)
                .filter(jwtTokenManager::isTokenValid)
                .orElse(null);

        if(refreshToken != null) {
            // refreshToken이 실존하는 토큰인지 검증한 뒤,
            // Access Token, Refresh Token 둘 다 재발행 해주는 코드가 필요.
            if(checkRefreshToken(refreshToken)) {
                reissueToken(response, refreshToken);
            }
            return;
        }

        // else 문은 지양하는 것이 가독성면에서 좋기에 쓰지 않음.
        // refreshToken이 요청에 없었다는 것은 Access Token을 보낸 경우밖에 없으니까,
        // Access Token을 검증하여 인가해주는 코드 필요.
    }

    private boolean checkRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken).isPresent();
    }

    private void reissueToken(HttpServletResponse response, String refreshToken) {
        Optional<User> optionalUser = userRepository.findByRefreshToken(refreshToken);
        User user = optionalUser.orElse(null);

        String newAccessToken = jwtTokenManager.createAccessToken(user);
        String newRefreshToken = jwtTokenManager.createRefreshToken();

        jwtTokenManager.updateRefreshToken(user, refreshToken);

        jwtTokenManager.sendAccessTokenAndRefreshToken(response, newAccessToken, newRefreshToken);
    }
}
