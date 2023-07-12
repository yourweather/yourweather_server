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


    public MimeMessage createMessage(String to)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to); //보내는 대상
        message.setSubject("유어웨더 인증 코드"); //제목

        String messageHtml="";
        messageHtml+= "<div style=\"margin: 10px; display: flex; justify-content: center;\">";
        messageHtml+= "<div>";
        messageHtml+= "<h1>유어웨더 메일 인증</h1>";
        messageHtml+= "<p>아래의 코드를 복사하여 붙여넣어 주세요!</p>";
        messageHtml+= "<div style=\"border: 1px solid rgb(213, 213, 213); border-radius: 7px; display: flex; justify-content: center; height: 200px;\">";
        messageHtml+= "<div style=\"text-align: center;\">";
        messageHtml+= "<h2 style=\"margin: 30px; margin-bottom: 50px;\">인증 코드</h2>";
        messageHtml+= "<span style=\"font-size: 130%;\">";
        messageHtml+= "<strong>" + code + "</strong>";
        messageHtml+= "</span>";
        messageHtml+= "</div>";
        messageHtml+= "</div>";
        messageHtml+= "</div>";
        messageHtml+= "</div>";

        message.setText(messageHtml, "utf-8", "html");//내용
        message.setFrom(new InternetAddress(id,"팀 유어웨더")); //보내는 사람

        return message;
    }

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

    public String getCode() {
        return code;
    }
}
