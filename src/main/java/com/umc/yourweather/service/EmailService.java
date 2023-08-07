package com.umc.yourweather.service;

import com.umc.yourweather.domain.MessageCreator;
import com.umc.yourweather.domain.entity.EmailCertify;
import com.umc.yourweather.repository.EmailCodeRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailCodeRepository emailCodeRepository;

    @Value("${spring.redis.life}")
    private long liveSpan;

    @Async
    public CompletableFuture<String> sendMessage(String to) throws Exception {
        MessageCreator messageCreator = new MessageCreator(emailSender);
        MimeMessage message = messageCreator.createMessage(to);
        log.info("message 생성: " + message.getContent());
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException("email 전송 실패: 받는 사람 이메일을 다시 확인해주세요");
        }

        String code = messageCreator.getCode();
        setCode(to, code);
        return CompletableFuture.completedFuture(code);
    }

    @Transactional
    private void setCode(String email, String code) {
        EmailCertify emailCertify = emailCodeRepository.findByEmail(email)
                .orElseGet(() -> {
                    EmailCertify toSave = EmailCertify.builder()
                            .code(code)
                            .email(email)
                            .liveSpan(liveSpan)
                            .build();
                    return emailCodeRepository.save(toSave);
                });

        emailCertify.certifyCodeRenewal(code);

        // orElseGet을 거친다면 불필요한 저장이 두 번 진행됨. 수정 필요
        emailCodeRepository.save(emailCertify);
    }

    @Transactional(readOnly = true)
    public boolean certifyingData(String email, String code) {
        EmailCertify emailCertify = emailCodeRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        email + " 계정의 인증 정보 조회 실패: 해당 이메일에 대한 정보가 없습니다."));

        String value = emailCertify.getCode();
        return value.equals(code);
    }
}
