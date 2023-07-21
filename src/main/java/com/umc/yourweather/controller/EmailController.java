package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.dto.ResponseDto;
import com.umc.yourweather.service.EmailService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestURI.EMAIL_URI)
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/send")
    public ResponseDto<Void> send(@RequestParam String email) {
        try {
            CompletableFuture<String> completableFutureCode = emailService.sendMessage(email);
            log.info("이메일 전송 완료. 인증코드: " + completableFutureCode.get());
            return ResponseDto.success("이메일 전송 완료.");
        } catch (Exception e) {
            return ResponseDto.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/certify")
    public ResponseDto<Boolean> certify(@RequestParam String email, @RequestParam String code) {
        boolean isCorrect = emailService.certifyingData(email, code);
        return isCorrect ?
                ResponseDto.success("이메일 인증이 성공했습니다.", isCorrect) :
                ResponseDto.fail(HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다. 인증코드를 다시 확인해주세요.");
    }
}
