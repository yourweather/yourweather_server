package com.umc.yourweather.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.response.AuthorizationResponseDto;
import com.umc.yourweather.response.ResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException(email + " : 해당하는 이메일을 가진 유저가 없습니다."));

        String accessToken = jwtTokenManager.createAccessToken(user);
        String refreshToken = jwtTokenManager.createRefreshToken();

        jwtTokenManager.updateRefreshToken(user, refreshToken);

        AuthorizationResponseDto authorizationResponseDto = AuthorizationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        ResponseDto<AuthorizationResponseDto> responseDto = ResponseDto.success(
                "로그인에 성공했습니다. email: " + email, authorizationResponseDto);

        String result = objectMapper.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(result);

        log.info("로그인에 성공했습니다. email: {}", email);
        log.info("발급된 AccessToken 만료 기간: {}", accessTokenExpiration);
    }

    private String extractEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userDetails.getUsername();
    }
}
