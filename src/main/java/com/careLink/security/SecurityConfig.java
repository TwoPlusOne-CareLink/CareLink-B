package com.careLink.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean //비밀번호 암호화
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Authorization 헤더를 통한 인증 사용하지 않음
        http.httpBasic(config -> config.disable());

        //폼을 통한 인증 사용하지 않음
        http.formLogin(config -> config.disable());

        //CORS 설정
//        http.cors(config -> {});

        //사이트간 요청 위조 방지 비활성화
        http.csrf(config -> config.disable());

        //서버 세션 비활성화
        http.sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //JWT 토큰 인증 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //요청 경로별 권한 설정
        http.authorizeHttpRequests(customizer -> customizer
                //방법1
//                .antMatchers("/user/**").permitAll() //회원만 가능
                .antMatchers("/user/**").hasAuthority("ROLE_USER") //회원만 가능
                //방법2
                //.antMatchers(HttpMethod.GET, "/board/list").hasAuthority("ROLE_USER") //ROLE_생략하면 안됨
                //.antMatchers(HttpMethod.POST, "/board/create").hasAnyRole("USER") //ROLE_ 붙이면 안됨
                //.antMatchers(HttpMethod.GET, "/board/read/*").hasAnyRole("USER")
                //.antMatchers(HttpMethod.PUT, "/board/update").hasAnyRole("USER")
                //.antMatchers(HttpMethod.DELETE, "/board/delete/*").hasAnyRole("USER")
                //.antMatchers(HttpMethod.POST, "/board/createWithAttach").hasAnyRole("USER")
                //.antMatchers(HttpMethod.GET, "/board/battach/*").hasAnyRole("USER")


                //그 이외의 모든 경로 허가
                .anyRequest().permitAll() //위에 사이트 외에는 권한다 허용
        );

        return http.build();
    }

    //인증 관리자 설정
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
//        return hierarchy;
//    }


}