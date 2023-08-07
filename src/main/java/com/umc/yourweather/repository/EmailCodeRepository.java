package com.umc.yourweather.repository;

import com.umc.yourweather.domain.entity.EmailCertify;
import java.util.Optional;

public interface EmailCodeRepository {
    Optional<EmailCertify> findByEmail(String email);

    EmailCertify save(EmailCertify emailCertify);
}
