package com.umc.yourweather.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    // loadUserByUsername이지만, CustomLoginFilter에서 email을 username으로 삽입했기 때문에
    // 실상은 loadUserByEmail이라고 생각해도 무방합니다.
    // 함수명은 메서드 구현때문에 어쩔 수 없음.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
