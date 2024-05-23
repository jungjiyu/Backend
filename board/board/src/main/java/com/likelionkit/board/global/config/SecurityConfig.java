package com.likelionkit.board.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /*
    spring security 가 필터 몸체
    context 에 인증된 유저의 정보를 저장해둔다. 정보를 바꿔야되면 나인지 인증을 해야되고 그런걸 도와준다.
     */
    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 를 써서 session 이 기본값으로 설정되어있어서
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST, "/api/users/sign-up").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers("/api/**").authenticated());


        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
