package com.umc.yourweather.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.response.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        ResponseDto<Void> responseDto = ResponseDto.fail(HttpStatus.BAD_REQUEST, "로그인 실패했습니다. 메시지: " + exception.getMessage());

        String result = objectMapper.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(result);
        log.info("로그인에 실패했습니다. 메시지: {}", exception.getMessage());
    }
}
