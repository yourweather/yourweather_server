package com.umc.yourweather.repository.redis;

import com.umc.yourweather.domain.entity.EmailCertify;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRedisRepository extends CrudRepository<EmailCertify, Long> {
    Optional<EmailCertify> findByEmail(String email);
}
