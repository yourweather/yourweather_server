package com.umc.yourweather.service;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.domain.entity.EmailCertify;
import com.umc.yourweather.repository.EmailCodeRepository;
import com.umc.yourweather.repository.test.EmailCodeTestRepository;
import com.umc.yourweather.service.test.EmailTestService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

class EmailServiceTest {

    private final JavaMailSender javaMailSender = new JavaMailSenderImpl();
    private final EmailCodeRepository emailCodeRepository = new EmailCodeTestRepository();
    private final EmailService emailService = new EmailTestService(javaMailSender, emailCodeRepository);

    @Test
    @DisplayName("getEmailCertify: Redis에 일치하는 email이 없을 때")
    public void test1() {
        // given
        final String email = "aaaa";
        final String code = "EMPTY";

        // when
        EmailCertify result = emailService.getEmailCertify(email, code);

        // then
        assertEquals(code, result.getCode());
    }

    @Test
    @DisplayName("getEmailCertify: Redis에 일치하는 email이 있을 때")
    public void test2() {
        // given
        final String email = "asdf@gmail.com";
        final String code = "EMPTY";

        // when
        EmailCertify result = emailService.getEmailCertify(email, code);

        // then
        assertNotEquals(code, result.getCode());
        assertEquals("DATA", result.getCode());
    }

    @Test
    @DisplayName("setCode: Redis에 일치하는 email이 없을 때")
    public void test3() {
        // given
        final String email = "aaaa";
        final String code = "CHANGED";

        // when
        EmailCertify emailCertify = emailService.setCode(email, code);

        // then
        assertEquals(code, emailCertify.getCode());
    }

    @Test
    @DisplayName("setCode: Redis에 일치하는 email이 있을 때")
    public void test4() {
        // given
        final String email = "asdf@gmail.com";
        final String code = "CHANGED";

        // when
        EmailCertify emailCertify = emailService.setCode(email, code);

        // then
        assertEquals(code, emailCertify.getCode());
    }

    @Test
    @DisplayName("certifyingData: Exception이 잘 throw 되는지 테스트")
    public void test5() {
        // given
        final String email = "aaaa";
        final String code = "ASDF";

        // when

        // then
        assertThrows(EntityNotFoundException.class, () -> emailService.certifyingData(email, code));
    }

    @Test
    @DisplayName("certifyingData: return 값 true 확인")
    public void test6() {
        // given
        final String email = "asdf@gmail.com"; // 이미 Repository에 기록된 키
        final String code = "DATA"; // 이미 Repository에 기록된 키에 대한 인증 코드

        // when
        boolean result = emailService.certifyingData(email, code);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("certifyingData: return 값 false 확인")
    public void test7() {
        // given
        final String email = "asdf@gmail.com"; // 이미 Repository에 있는 키
        final String code = "DIFF"; // 기록된 것과 다른 인증 코드

        // when
        boolean result = emailService.certifyingData(email, code);

        // then
        assertFalse(result);
    }
}