package com.umc.yourweather.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetailsService;
import com.umc.yourweather.domain.ReIssueTokenProvider;
import com.umc.yourweather.domain.enums.Role;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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

    private final ReIssueTokenProvider reIssueTokenProvider;

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
                    authorize.requestMatchers(RequestURI.ADVERTISEMENT_URI + "/add-advertisement")
                            .hasRole("ADMIN");
                    authorize.requestMatchers(RequestURI.ADVERTISEMENT_URI + "/edit-advertisement")
                            .hasRole("ADMIN");
                    authorize.requestMatchers(RequestURI.ADVERTISEMENT_URI + "/delete-advertisement")
                            .hasRole("ADMIN");
                    authorize.anyRequest().permitAll();
                });

        // 우리가 만든 CustomLoginFilter를 LogoutFilter 이후에 꽂아넣어준다.
        // 원래 시큐리티 필터가 LogoutFilter 이후에 로그인 필터를 동작 시킨다.
        http.addFilterAfter(customOAuthLoginFilter(), LogoutFilter.class);
        http.addFilterAfter(customLoginFilter(), CustomOAuthLoginFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomLoginFilter.class);

        http.cors(httpSecurityCorsConfigurer -> corsConfigurationSource());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
        return new JwtAuthenticationFilter(jwtTokenManager, userRepository, reIssueTokenProvider);
    }
}
