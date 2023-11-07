package com.careLink.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

//    private final JwtTokenProvider jwtTokenProvider;

    @Bean //비밀번호 암호화
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()

                // 세션 사용안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // URL 관리
                .authorizeRequests()
                    .antMatchers("/**").permitAll();
//                .antMatchers("/user/**").hasRole("ROLE_USER") ///user/이후 주소는 모두 "ROLE_USER"라는 권한을 가진 회원만 접근 허용
//                .antMatchers("/doctor/**").hasRole("ROLE_DOCTOR") ///doctor/이후 주소는 모두 "ROLE_DOCTOR"라는 권한을 가진 회원만 접근 허용
//                .antMatchers("/admin/**").hasRole("ROLE_ADMIN") ///admin/이후 주소는 모두 "ROLE_ADMIN"라는 권한을 가진 회원만 접근 허용
//                .anyRequest().permitAll()
//                .anyRequest().authenticated() //authenticated() : 로그인 인증만 해야됨
//                .and()
//
//                .formLogin() // ->  API 서버와 같이 로그인 페이지가 필요하지 않거나, 다른 로그인 메커니즘을 사용해야 하는 경우에는 .formLogin()을 사용하지 않을 수 있습니다.
//                    .loginPage("/login") // 로그인 페이지 지정
//                    .permitAll() // 로그인 페이지는 모두 허용
//                    .defaultSuccessUrl("/"); // 로그인 성공 후 이동할 페이지
//                .and()
//
//                .logout()
//                    .permitAll(); // 로그아웃은 모두 허용
//                .and()
//
//                 JwtAuthenticationFilter를 먼저 적용
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}