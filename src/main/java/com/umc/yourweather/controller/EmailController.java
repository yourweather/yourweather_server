package com.umc.yourweather.controller;

import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/send")
    public ResponseDto<Void> send(@RequestParam String email) {
        try {
            emailService.sendMessage(email);
            return ResponseDto.success("이메일 전송 완료.");
        } catch (Exception e) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
