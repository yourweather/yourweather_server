package com.umc.yourweather.jwt;

import com.umc.yourweather.domain.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenManagerTest {
    @Autowired
    private JwtTokenManager jwtTokenManager;
    private User user;

    @BeforeEach
    void setUser() {
        user = User.builder()
                .email("asdf@gmail.com")
                .password("asdf")
                .nickname("ffff")
                .platform("google")
                .build();
    }

    @Test
    @DisplayName("createAccessToken 테스트")
    void test1() {
        //given
        String accessToken = "";

        //when
        accessToken = jwtTokenManager.createAccessToken(user);

        //then
        System.out.println(accessToken);
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("createRefreshToken 테스트")
    void test2() {
        //given
        String refreshToken = "";

        //when
        refreshToken = jwtTokenManager.createRefreshToken();

        //then
        System.out.println(refreshToken);
        assertNotNull(refreshToken);
    }

    @Test
    @DisplayName("sendAccessTokenAndRefreshToken 테스트")
    void test3() throws IOException {
        //given
        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = servletContainer.getResponse();

        String accessToken = jwtTokenManager.createAccessToken(user);
        String refreshToken = jwtTokenManager.createRefreshToken();

        //when
        jwtTokenManager.sendAccessTokenAndRefreshToken(httpServletResponse, accessToken, refreshToken);

        //then
        System.out.println(httpServletResponse.getHeader("Authorization"));
        System.out.println(httpServletResponse.getHeader("Authorization-refresh"));

        assertEquals(accessToken, httpServletResponse.getHeader("Authorization"));
        assertEquals(refreshToken, httpServletResponse.getHeader("Authorization-refresh"));
    }

    @Test
    @DisplayName("extractToken 테스트")
    void test4() {
        //given
        String accessToken = jwtTokenManager.createAccessToken(user);
        String refreshToken = jwtTokenManager.createRefreshToken();

        //when
        String accessTokenFromManager = jwtTokenManager.extractToken("Bearer " + accessToken)
                .orElseGet(null);
        String refreshTokenFromManager = jwtTokenManager.extractToken("Bearer " + refreshToken)
                .orElseGet(null);

        //then
        System.out.println(accessTokenFromManager);
        System.out.println(refreshTokenFromManager);

        assertEquals(accessToken, accessTokenFromManager);
        assertEquals(refreshToken, refreshTokenFromManager);
    }

    @Test
    @DisplayName("isTokenValid 테스트")
    void test5() {
        // 테스트 통과!
        // 단 토큰 생성, 검증로직이 시크릿키와 토큰 만료 시간에 강하게 의존하고 있기 때문에 어느정도 이 의존성은 풀어주자.
        // 막 사용해도 상관없는 더미를 만들어서, 메서드에 넣어도 기대값을 얻을 수 있도록!
//        //given
//        Date now = new Date();
//        String token = JWT.create()
//                .withSubject("AccessToken")
//                .withExpiresAt(new Date(now.getTime() - tokenExpriation * 2))
//                .sign(Algorithm.HMAC512(secret));
//
//        //when
//        boolean isvalid = jwtTokenManager.isTokenValid(token);
//
//        //then
//        assertEquals(isvalid, false);
    }
}