package com.umc.yourweather.domain.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "email_certify")
public class EmailCertify {

    @Id
    private String id;

    @Indexed
    private final String email;

    @TimeToLive
    private final long liveSpan;

    private String code;
    private int count;

    @Builder
    public EmailCertify(String email, String code, long liveSpan) {
        this.email = email;
        this.code = code;
        this.liveSpan = liveSpan;
    }

    public String getCode() {
        return code;
    }

    public void certifyCodeRenewal(String code) {
        if(code == null)
            throw new IllegalArgumentException("이메일 인증 요청 갱신 실패: 인자로 들어온 이메일 인증 코드가 null입니다.");

        if(count + 1 > 5)
            throw new IllegalStateException("이메일 인증 요청 갱신 실패: 인증 요청 횟수가 5회를 초과했습니다.");

        this.code = code;
        this.count++;
    }
}
