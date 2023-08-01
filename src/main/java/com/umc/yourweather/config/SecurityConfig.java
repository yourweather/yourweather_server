package com.umc.yourweather.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetailsService;
import com.umc.yourweather.jwt.JwtTokenManager;
import com.umc.yourweather.jwt.filter.CustomLoginFilter;
import com.umc.yourweather.jwt.filter.CustomOAuthLoginFilter;
import com.umc.yourweather.jwt.filter.JwtAuthenticationFilter;
import com.umc.yourweather.jwt.handler.LoginFailureHandler;
import com.umc.yourweather.jwt.handler.LoginSuccessHandler;
import com.umc.yourweather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenManager jwtTokenManager;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable))

                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> {
                    // /signup에 대한건 다 허락.
                    authorize.requestMatchers(RequestURI.USER_URI + "/signup").permitAll();
                    authorize.requestMatchers(RequestURI.USER_URI + "/login").permitAll();
                    authorize.requestMatchers(RequestURI.USER_URI + "/oauth-login").permitAll();

                    // 그 외의 모든 요청은 인증이 되어있어야함.
                    authorize.anyRequest().authenticated();
                });

        // 우리가 만든 CustomLoginFilter를 LogoutFilter 이후에 꽂아넣어준다.
        // 원래 시큐리티 필터가 LogoutFilter 이후에 로그인 필터를 동작 시킨다.
        http.addFilterAfter(customOAuthLoginFilter(), LogoutFilter.class);
        http.addFilterAfter(customLoginFilter(), CustomOAuthLoginFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomLoginFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CustomLoginFilter customLoginFilter() {
        CustomLoginFilter customLoginFilter = new CustomLoginFilter(objectMapper,
                authenticationManager());

        customLoginFilter.setAuthenticationManager(authenticationManager());
        customLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);

        return customLoginFilter;
    }

    @Bean
    public CustomOAuthLoginFilter customOAuthLoginFilter() {
        CustomOAuthLoginFilter customOAuthLoginFilter = new CustomOAuthLoginFilter(objectMapper,
                authenticationManager());

        customOAuthLoginFilter.setAuthenticationManager(authenticationManager());
        customOAuthLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customOAuthLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);

        return customOAuthLoginFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenManager, userRepository);
    }
}
