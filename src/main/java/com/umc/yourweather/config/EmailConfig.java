package com.umc.yourweather.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${mail.smtp.starttls.required}")
    private boolean starttls_required;

    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;

    @Value("${AdminMail.id}")
    private String id;

    @Value("${AdminMail.password}")
    private String password;

}
