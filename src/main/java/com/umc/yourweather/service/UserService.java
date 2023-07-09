package com.umc.yourweather.service;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.dto.SignupRequestDto;
import com.umc.yourweather.repository.jpa.UserJpaRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserJpaRepository userRepository;

    @Transactional
    public String signup(@Valid SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        if (password == null) {
            password = UUID.randomUUID().toString();
        }
        String nickname = signupRequestDto.getNickname();
        String platform = signupRequestDto.getPlatform();

        // 이메일 중복 검증 로직
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 해당 이메일로 가입된 유저가 존재합니다.");
        }

        User newUser = User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .platform(platform)
            .build();
        userRepository.save(newUser);
        return "회원 가입 완료";
    }
}
