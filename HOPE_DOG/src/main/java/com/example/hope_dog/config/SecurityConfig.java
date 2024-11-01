package com.example.hope_dog.config;

import com.example.hope_dog.service.member.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
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
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
<<<<<<< HEAD
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/img/**",     // img 경로 추가
                                "/images/**",
                                "/fonts/**",   // 폰트 파일 경로도 추가
                                "/assets/**"   // 기타 정적 자원 경로 추가
                        ).permitAll()
<<<<<<< HEAD
                        .requestMatchers("/", "/member/**", "/center/**", "/main/**", "/admin/**").permitAll()
=======
                        .requestMatchers("/", "/member/**", "/center/**", "/main/**", "/admin/**", "/adopt/**", "/volun/**", "/car/**", "/centermypage/**","/dona/**", "/mypgae/**", "/commu/**", "/notice/**").permitAll()
>>>>>>> 5fd740137e290fd1d1f20981ae02a513667dc9a6
                        .anyRequest().authenticated()
=======
                        .requestMatchers("/**").permitAll()  // 모든 요청 허용
>>>>>>> 894052b1d6e421f51ce505709682b0abbeaed285
=======
                        .requestMatchers("/**").permitAll()
>>>>>>> 48982e112f40a3ce5b9d9336dc23cea2d0838ce0
                )
                .formLogin(form -> form
                        .loginPage("/member/login-select")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/member/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oauth2LoginSuccessHandler())
                        .failureUrl("/member/login?error=true")
                );

        return http.build();
    }

    @Bean
    public OAuth2LoginSuccessHandler oauth2LoginSuccessHandler() {
        return new OAuth2LoginSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}