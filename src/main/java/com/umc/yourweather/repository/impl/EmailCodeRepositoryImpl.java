package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.EmailCertify;
import com.umc.yourweather.repository.EmailCodeRepository;
import com.umc.yourweather.repository.redis.EmailCodeRedisRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailCodeRepositoryImpl implements EmailCodeRepository {

    private final EmailCodeRedisRepository emailCodeRedisRepository;

    @Override
    public Optional<EmailCertify> findByEmail(String email) {
        return emailCodeRedisRepository.findByEmail(email);
    }

    @Override
    public EmailCertify save(EmailCertify emailCertify) {
        return emailCodeRedisRepository.save(emailCertify);
    }
}
