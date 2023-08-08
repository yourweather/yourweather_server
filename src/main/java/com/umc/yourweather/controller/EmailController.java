package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    @Operation(summary = "이메일 전송", description = "인증을 위한 이메일을 전송합니다. 인증코드를 유저로부터 입력받아 /certify 요청을 보내면 유효한 코드인지 알 수 있습니다. 이메일 전송이 5번 초과되면 더 이상 시도할 수 없습니다. 5분을 기다려 캐시가 삭제되거나 혹은 다른 이메일 주소로 시도해야합니다.")
    @Parameter(name = "email", description = "메일을 보낼 사용자 이메일입니다.", in = ParameterIn.QUERY)
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
    @Operation(summary = "이메일 인증", description = "이메일로부터 전송받은 인증 코드를 인증합니다. result 값이 true면 인증이 완료된 것입니다.")
    @Parameter(name = "email", description = "인증코드를 받은 email 주소")
    @Parameter(name = "code", description = "인증코드")
    public ResponseDto<Boolean> certify(@RequestParam String email, @RequestParam String code) {
        boolean isCorrect = emailService.certifyingData(email, code);
        return isCorrect ?
                ResponseDto.success("이메일 인증이 성공했습니다.", isCorrect) :
                ResponseDto.fail(HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다. 인증코드를 다시 확인해주세요.");
    }
}
