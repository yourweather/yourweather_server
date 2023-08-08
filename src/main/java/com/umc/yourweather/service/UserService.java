package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.enums.Role;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.response.AuthorizationResponseDto;
import com.umc.yourweather.response.UserResponseDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.exception.UserNotFoundException;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    @Value("${jwt.access.header}")
    private String accessTokenHeader;

    @Value("${jwt.refresh.header}")
    private String refreshTokenHeader;

    @Transactional
    public AuthorizationResponseDto signup(@Valid SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        password = passwordEncoder.encode(password);
        String nickname = signupRequestDto.getNickname();
        String platform = signupRequestDto.getPlatform();

        // 이메일 중복 검증 로직
        userRepository.findByEmail(email).ifPresent(
            user -> {
                throw new RuntimeException("이미 해당 이메일로 가입된 유저가 존재합니다.");
            }
        );

        User user = User.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .platform(platform)
            .role(Role.ROLE_USER)
            .isActivate(true)
            .build();

        // 회원 가입으로 DB에 데이터를 넣는 책임과 token을 제공하는 dto를 만드는 책임은 분리를 하는게 더 좋아보임
        return getSignupResponse(userRepository.save(user));
    }

    protected AuthorizationResponseDto getSignupResponse(User user) {
        String accessToken = jwtTokenManager.createAccessToken(user);
        String refreshToken = jwtTokenManager.createRefreshToken();

        return AuthorizationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserResponseDto mypage(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
            .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));
        return new UserResponseDto(user.getNickname(), user.getEmail());
    }

    @Transactional
    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto,
        CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
            .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));

        String password = changePasswordRequestDto.getPassword();
        user.changePassword(passwordEncoder.encode(password));
        return "비밀번호 변경 완료";
    }

    @Transactional
    public UserResponseDto withdraw(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
            () -> new UserNotFoundException("등록된 사용자가 없습니다.")
        );

        // Unactivate
        user.unActivate();
        return new UserResponseDto(user.getNickname(), user.getEmail());
    }
}
