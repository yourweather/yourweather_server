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

    private final String code = createKey();

    @Value("${AdminMail.id}")
    private String id;

    public static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                case 0 -> key.append((char) ((int) (rnd.nextInt(26)) + 97));

                //  A~Z
                case 1 -> key.append((char) ((int) (rnd.nextInt(26)) + 65));

                // 0~9
                case 2 -> key.append((rnd.nextInt(10)));
            }
        }
        return key.toString();
    }
}
