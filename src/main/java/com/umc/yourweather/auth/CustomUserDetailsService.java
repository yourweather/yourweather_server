package com.umc.yourweather.auth;

import com.umc.yourweather.domain.User;
import com.umc.yourweather.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // loadUserByUsername이지만, CustomLoginFilter에서 email을 username으로 삽입했기 때문에
    // 실상은 loadUserByEmail이라고 생각해도 무방합니다.
    // 함수명은 메서드 구현때문에 어쩔 수 없음.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(email + " : 해당 이메일을 가진 유저가 존재하지 않습니다."));

        return new CustomUserDetails(user);
    }
}
