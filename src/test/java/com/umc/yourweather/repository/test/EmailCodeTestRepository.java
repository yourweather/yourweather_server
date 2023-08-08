package com.umc.yourweather.repository.test;

import com.umc.yourweather.domain.entity.EmailCertify;
import com.umc.yourweather.repository.EmailCodeRepository;
import java.util.Optional;

public class EmailCodeTestRepository implements EmailCodeRepository {

    @Override
    public Optional<EmailCertify> findByEmail(String email) {
        if(email.equals("asdf@gmail.com")) {
            EmailCertify result = EmailCertify.builder()
                    .email(email)
                    .code("DATA")
                    .liveSpan(10)
                    .build();
            return Optional.of(result);
        }

        return Optional.empty();
    }

    @Override
    public EmailCertify save(EmailCertify emailCertify) {
        return emailCertify;
    }
}
