package com.umc.yourweather.service;

import com.umc.yourweather.domain.MessageCreator;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public String sendMessage(String to)throws Exception {
        MessageCreator messageCreator = new MessageCreator(emailSender);
        MimeMessage message = messageCreator.createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return messageCreator.getCode();
    }
}
