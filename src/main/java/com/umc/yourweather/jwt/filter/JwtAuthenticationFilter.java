package com.umc.yourweather.jwt.filter;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // login 요청이 들어오면 필터에서 토큰 검증 건너뜀.
    private static final String[] NO_CHECK_URI_ARRAY = {"/login", "/api/users/signup"};
    private static final List<String> NO_CHECK_URIS = new ArrayList<>(Arrays.asList(NO_CHECK_URI_ARRAY));

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(NO_CHECK_URIS.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtTokenManager.extractRefreshToken(request)
                .filter(jwtTokenManager::isTokenValid)
                .orElse(null);

        String accessToken = jwtTokenManager.extractAccessToken(request)
                .filter(jwtTokenManager::isTokenValid)
                .orElse(null);

        if(refreshToken != null) {
            // refreshToken이 실존하는 토큰인지 검증한 뒤,
            // Access Token, Refresh Token 둘 다 재발행 해주는 코드가 필요.
            if(checkRefreshToken(refreshToken)) {
                reissueToken(response, refreshToken);
            }
            // 만약 refresh 토큰이 온 경우에는 더 이상 인증을 진행시키지 않고 필터 진행 자체를 끊어버린다.
            return;
        }

        // else 문은 지양하는 것이 가독성면에서 좋기에 쓰지 않음.
        // refreshToken이 요청에 없었다는 것은 Access Token을 보낸 경우밖에 없으니까,
        // Access Token을 검증하여 인가해주는 코드 필요.
        if(checkEmailInAccessToken(accessToken)) {
            String email = jwtTokenManager.extractEmail(accessToken).get();

            if(checkEmailIsInDB(email)) {
                // 쿼리가 두 번 날아갈 것 같지만 영속성 컨텍스트에 잘 저장하고 있을테니 상관 X
                User user = userRepository.findByEmail(email).get();

                setAuthentication(user);
                filterChain.doFilter(request, response);
            }
        }
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

    private boolean checkEmailInAccessToken(String accessToken) {
        return jwtTokenManager.extractEmail(accessToken).isPresent();
    }

    private boolean checkEmailIsInDB(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void setAuthentication(User user) {
        String password = user.getPassword();

        // 이 경우에는 타 플랫폼(네이버, 구글, 카카오)로 회원가입한 사람들이다.
        // 이 사람들에게 비밀번호 필ㄷ는 의미가 없는 것이니, 대충 아무거나 막 랜덤한거로 해서 넣는다.
        if(password == null) {
            password = UUID.randomUUID().toString();
        }

        UserDetails userDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        // 세션에 Authentication 저장.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
