package com.fourcut.diary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.dto.ErrorResponse;
import com.fourcut.diary.filter.CustomJwtAuthenticationFilter;
import com.fourcut.diary.filter.JwtExceptionHandlerFilter;
import com.fourcut.diary.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenManager jwtTokenManager;

    private static final String[] AUTH_WHITELIST = {
            "/error",
            "/user/social-signup",
            "/user/social-login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(AUTH_WHITELIST).permitAll()
                            .anyRequest().authenticated();
                })
                .headers(header -> {
                    header
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                });

        http
                .addFilterBefore(new CustomJwtAuthenticationFilter(jwtTokenManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionHandlerFilter(), CustomJwtAuthenticationFilter.class);

        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(((request, response, authException) -> {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorMessage.NOT_FOUND_PATH.getMessage());
                response.setContentType("application/json; charset=UTF-8");
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            }));
        });

        return http.build();
    }
}
