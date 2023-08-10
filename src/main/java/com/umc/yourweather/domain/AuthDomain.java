package com.umc.yourweather.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.request.LoginRequestDto;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


@RequiredArgsConstructor
public class AuthDomain {
    private static final List<String> CONTENT_TYPE = Arrays.asList(
            "application/json",
            "application/json; charset=UTF-8",
            "application/json;charset=UTF-8");

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    public Authentication authentication(String contentType, String jsonBody)
            throws JsonProcessingException {
        // 여기서 request의 body를 ObjectMapper로 읽고 로그인 처리를 해주는 것!

        // contentType이 기재되지 않았거나 application/json이 아니면 에러를 던진다.
        if (contentType == null || !CONTENT_TYPE.contains(contentType)) {
            throw new AuthenticationServiceException(
                    "지원되지 않는 Content-Type입니다. " + contentType);
        }

        LoginRequestDto mappedBody = objectMapper.readValue(jsonBody, LoginRequestDto.class);

        String email = mappedBody.getEmail();
        String password = mappedBody.getPassword();

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email,
                password);
        return authenticationManager.authenticate(authReq);
    }
}
