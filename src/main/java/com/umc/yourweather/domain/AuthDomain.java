package com.umc.yourweather.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


@RequiredArgsConstructor
public class AuthDomain {
    private static final String CONTENT_TYPE = "application/json";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    public Authentication authentication(String contentType, String jsonBody)
            throws JsonProcessingException {
        // 여기서 request의 body를 ObjectMapper로 읽고 로그인 처리를 해주는 것!

        // contentType이 기재되지 않았거나 application/json이 아니면 에러를 던진다.
        if (contentType == null || !contentType.equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException(
                    "지원되지 않는 Content-Type입니다. " + contentType);
        }

        Map<String, String> mappedBody = objectMapper.readValue(jsonBody, Map.class);

        String email = mappedBody.get(EMAIL_KEY);
        String password = mappedBody.get(PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email,
                password);
        return authenticationManager.authenticate(authReq);
    }
}
