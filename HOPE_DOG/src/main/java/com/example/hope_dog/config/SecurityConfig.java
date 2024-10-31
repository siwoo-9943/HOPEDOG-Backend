package com.example.hope_dog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",     // img 경로 추가
                                "/images/**",
                                "/fonts/**",   // 폰트 파일 경로도 추가
                                "/assets/**"   // 기타 정적 자원 경로 추가
                        ).permitAll()
                        .requestMatchers("/", "/member/**", "/center/**", "/main/**", "/admin/**", "/adopt/**", "/volun/**", "/car/**", "/centermypage/**","/dona/**", "/mypgae/**", "/commu/**", "/notice/**").permitAll()
                        .anyRequest().authenticated()
=======
                        .requestMatchers("/**").permitAll()  // 모든 요청 허용
>>>>>>> 894052b1d6e421f51ce505709682b0abbeaed285
                )
                .formLogin(form -> form
                        .loginPage("/member/login-select")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}