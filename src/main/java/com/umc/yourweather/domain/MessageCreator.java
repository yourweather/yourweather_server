package com.umc.yourweather.domain;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class MessageCreator {
    private final JavaMailSender emailSender;

    private final String code;

    @Value("${AdminMail.id}")
    private String id;



}
