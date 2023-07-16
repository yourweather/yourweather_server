package com.umc.yourweather.service;

import com.umc.yourweather.domain.MessageCreator;
import com.umc.yourweather.repository.redis.EmailCodeRedisRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailCodeRedisRepository emailCodeRedisRepository;

    public String sendMessage(String to) throws Exception {
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
        return code;
    }

    private void setCode(String email, String code) {
        emailCodeRedisRepository.setData(email, code);
    }
}
