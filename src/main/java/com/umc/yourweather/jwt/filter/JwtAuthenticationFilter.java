package com.umc.yourweather.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.ReIssueTokenProvider;
import com.umc.yourweather.domain.ReIssuedToken;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.response.ResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private static final String[] NO_CHECK_URI_ARRAY = {
            RequestURI.USER_URI + "/login",
            RequestURI.USER_URI + "/signup",
            RequestURI.USER_URI + "/oauth-login",
            RequestURI.USER_URI + "/verify-user-email",

            RequestURI.EMAIL_URI + "/send",
            RequestURI.EMAIL_URI + "/certify",

            RequestURI.ADVERTISEMENT_URI + "/get-advertisement",

            "/swagger-ui/index.html",
            "/favicon.ico"
    };

    private static final List<String> NO_CHECK_URIS = new ArrayList<>(
            Arrays.asList(NO_CHECK_URI_ARRAY));

    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ReIssueTokenProvider reIssueTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (NO_CHECK_URIS.contains(request.getRequestURI())
                || request.getRequestURI().contains("/swagger-ui")
                || request.getRequestURI().contains("/v3")) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isRefreshTokenValid;
        boolean isAccessTokenValid;

        String refreshToken = jwtTokenManager.extractRefreshToken(request)
                .orElse(null);

        String accessToken = jwtTokenManager.extractAccessToken(request)
                .orElse(null);

        isRefreshTokenValid = jwtTokenManager.isRefreshTokenValid(refreshToken);
        isAccessTokenValid = jwtTokenManager.isAccessTokenValid(accessToken);

        if (refreshToken != null) {
            // refreshToken이 실존하는 토큰인지 검증한 뒤,
            // Access Token, Refresh Token 둘 다 재발행 해주는 코드가 필요.

            if (!isRefreshTokenValid) {
                setBadRequestResponse(response,
                        "Token 재발급 실패: 유효한 Refresh Token이 아닙니다. login을 통해 재발급 받으세요.");
                return;
            }

            if (checkRefreshToken(refreshToken)) {
                ReIssuedToken reIssuedToken = reIssueTokenProvider.reissueToken(refreshToken);
                jwtTokenManager.sendAccessTokenAndRefreshToken(response,
                        reIssuedToken.getAccessToken(),
                        reIssuedToken.getRefreshToken());
            } else {
                // refresh token은 유효기간이 만료되었거나 해서 상하면 자동으로 null처리 됨.
                // 따라서 여기까지 와서 DB에 있는지 검사를 했는데 이런다면 악의적인 외부 접근일 가능성이 높음
                setBadRequestResponse(response,
                        "Token 재발급 실패: DB에 해당 Refresh Token에 대한 기록이 없습니다. 의도하지 않고 일어나기 어려운 상황입니다. 백엔드 팀에게 문의해주세요.");
            }
            // 만약 refresh 토큰이 온 경우에는 더 이상 인증을 진행시키지 않고 필터 진행 자체를 끊어버린다.
            return;
        }

        if (accessToken != null) {
            // else 문은 지양하는 것이 가독성면에서 좋기에 쓰지 않음.
            // refreshToken이 요청에 없었다는 것은 Access Token을 보낸 경우밖에 없으니까,
            // Access Token을 검증하여 인가해주는 코드 필요.

            if (!isAccessTokenValid) {
                setBadRequestResponse(response, "인증 실패: 유효하지 않은 Access Token입니다.");
            }

            Optional<String> optionalEmail = jwtTokenManager.extractEmail(accessToken);
            if (optionalEmail.isPresent()) {
                String email = optionalEmail.get();

                Optional<User> optionalUser = userRepository.findByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    setAuthentication(user);
                    filterChain.doFilter(request, response);
                    return;
                }

                setBadRequestResponse(response, "인증 실패: Access Token에 있는 email 정보에 해당하는 유저가 없습니다.");
                return;
            }

            setBadRequestResponse(response,
                    "인증 실패: Access Token에서 email 정보를 추출하지 못했습니다. 의도하지 않고 일어나기 어려운 상황입니다. 백엔드 팀에 문의해주세요.");
            return;
        }

        // refresh token, access token 둘 다 null인 상황
        setBadRequestResponse(response,
                "인증 실패: Access Token, Refresh Token이 모두 없습니다. 요청 시에 둘 중 하나를 꼭 헤더에 넣어 보내주세요.");
    }

    private void setBadRequestResponse(HttpServletResponse response, String message)
            throws IOException {
        ResponseDto<Void> responseDto = ResponseDto.fail(HttpStatus.BAD_REQUEST, message);
        String result = objectMapper.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(result);
    }

    private boolean checkRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken).isPresent();
    }

    private void setAuthentication(User user) {

        UserDetails userDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        // 세션에 Authentication 저장.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
