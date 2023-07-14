package com.umc.yourweather.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.umc.yourweather.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원 가입 테스트")
    void signup() throws Exception {
        mockMvc.perform(post("/api/test/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"email\":\"test@gmail.com.\", \"password\" : \"1234\",\"nickname\":\"tester\",\"platform\":\"google\"}"))
            .andDo(print());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login(){

    }
}