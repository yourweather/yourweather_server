package com.umc.yourweather.service;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.enums.Platform;
import com.umc.yourweather.domain.enums.Role;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.request.ChangePasswordRequestDto;
import com.umc.yourweather.request.ResetPasswordRequestDto;
import com.umc.yourweather.response.AuthorizationResponseDto;
import com.umc.yourweather.response.ChangePasswordResponseDto;
import com.umc.yourweather.response.UserResponseDto;
import com.umc.yourweather.request.SignupRequestDto;
import com.umc.yourweather.exception.UserNotFoundException;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.repository.UserRepository;
import com.umc.yourweather.response.VerifyEmailResponseDto;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
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

    @Value("${admin.secret}")
    private String secretKey;

    @Transactional
    public AuthorizationResponseDto signup(
            @Valid SignupRequestDto signupRequestDto,
            String secretKey) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        password = passwordEncoder.encode(password);
        String nickname = signupRequestDto.getNickname();
        Platform platform = signupRequestDto.getPlatform();

        // 이메일 중복 검증 로직
        userRepository.findByEmail(email).ifPresent(
                user -> {
                    throw new RuntimeException("이미 해당 이메일로 가입된 유저가 존재합니다.");
                }
        );

        User user;
        if(secretKey.equals(this.secretKey)) {
            user = User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .platform(platform)
                    .role(Role.ROLE_ADMIN)
                    .isActivate(true)
                    .build();
        } else {
            user = User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .platform(platform)
                    .role(Role.ROLE_USER)
                    .isActivate(true)
                    .build();
        }

        // 회원 가입으로 DB에 데이터를 넣는 책임과 token을 제공하는 dto를 만드는 책임은 분리를 하는게 더 좋아보임
        return getSignupResponse(userRepository.save(user));
    }

    @Transactional
    protected AuthorizationResponseDto getSignupResponse(User user) {
        String accessToken = jwtTokenManager.createAccessToken(user);
        String refreshToken = jwtTokenManager.createRefreshToken();

        user.updateRefreshToken(refreshToken);

        return AuthorizationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserResponseDto mypage(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));
        return new UserResponseDto(user.getNickname(), user.getEmail(), user.getPlatform());
    }

    @Transactional
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto,
            CustomUserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));

        String password = changePasswordRequestDto.getPassword();
        String newPassword = changePasswordRequestDto.getNewPassword();


        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ChangePasswordResponseDto.builder()
                    .success(false)
                    .message("요청으로 들어온 기존 비밀번호가 DB에 있는 정보와 일치하지 않습니다.")
                    .occurredByDB(true)
                    .occurredByPassword(false)
                    .build();
        }

        if (password.equals(newPassword)) {
            return ChangePasswordResponseDto.builder()
                    .success(false)
                    .message("변경하려는 비밀번호가 기존 비밀번호와 동일합니다.")
                    .occurredByDB(false)
                    .occurredByPassword(true)
                    .build();
        }

        user.changePassword(passwordEncoder.encode(newPassword));

        return ChangePasswordResponseDto.builder()
                .success(true)
                .message("비밀번호 변경 완료")
                .occurredByDB(false)
                .occurredByPassword(false)
                .build();
    }

    @Transactional
    public String resetPassword(ResetPasswordRequestDto resetPasswordRequestDto,
            CustomUserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail())
                .orElseThrow(() -> new UserNotFoundException("등록된 사용자가 없습니다."));

        String password = resetPasswordRequestDto.getPassword();

        user.changePassword(passwordEncoder.encode(password));

        return "비밀번호 변경 완료";
    }

    @Transactional
    public UserResponseDto changeNickname(String nickname, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("닉네임 변경 실패: 등록된 사용자가 없습니다."));

        user.changeNickname(nickname);
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto withdraw(CustomUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new UserNotFoundException("등록된 사용자가 없습니다.")
        );

        // Unactivate
        user.unActivate();
        return new UserResponseDto(user.getNickname(), user.getEmail(), user.getPlatform());
    }

    public VerifyEmailResponseDto verifyEmail(String email) {

        List<String> platforms = Arrays.stream(Platform.values())
                .map(Enum::toString)
                .toList();
        System.out.println("platforms = " + platforms);

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserNotFoundException("email 검증 실패: 해당 email을 가진 유저가 없습니다."));

        if(user.getPlatform().equals(Platform.YOURWEATHER))
            return VerifyEmailResponseDto
                    .builder()
                    .oauth(false)
                    .platform(Platform.YOURWEATHER)
                    .build();

        return VerifyEmailResponseDto.builder()
                .oauth(true)
                .platform(user.getPlatform())
                .build();
    }
}
