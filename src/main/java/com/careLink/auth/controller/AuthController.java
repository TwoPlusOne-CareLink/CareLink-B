package com.careLink.auth.controller;

import com.careLink.ResultDto;
import com.careLink.auth.dto.SignInDto;
import com.careLink.auth.dto.LoginResultDto;
import com.careLink.auth.service.AuthService;
import com.careLink.entity.MemberEntity;
import com.careLink.security.AppUserDetails;
import com.careLink.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//회원 가입 | 로그인
@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup") //일반 회원가입
    public ResponseEntity<ResultDto> signup(@RequestBody MemberEntity memberEntity) {
        log.info("일반회원가입 시작");
        memberEntity.join("ROLE_USER",1); //회원권한, 회원탈퇴여부 부여
        String memberId = authService.signUp(memberEntity); //회원가입 성공 후 고유 번호 리턴
        return new ResponseEntity(new ResultDto(true,memberId + " 회원가입 성공"), HttpStatus.OK);

    }

    @PostMapping("/login") //로그인 -> 토큰 추가하고 service 리턴 타입 String로 변경예정
    public ResponseEntity<LoginResultDto> login(@RequestBody SignInDto signInDto) {

        //아이디와 패스워드 검증-------------------------------------
        //아이디와 비밀번호를 가지고 있는 객체 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(signInDto.getMemberId(), signInDto.getPassword());

        //DB에서 사용자 정보와 일치하는지 검사해서(비밀번호 체킹)
        //맞을 경우 Authentication 객체 리턴
        //맞지 않을 경우 403 예외 발생
        Authentication authentication = authenticationManager.authenticate(authToken); // -----> 입력한 비밀번호와 로그인한 아이디의 회원정보에서 비밀번호와 체킹

        //Spring Security에 인증 객체를 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //AccessToken 생성 ---------------------------------------
        String accessToken = jwtUtil.createToken(signInDto.getMemberId());

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal(); //앞에서 로그인해서 얻은 아이디 값의 회원정보 받아오는 메서드(getPrincipal());
        MemberEntity memberEntity = appUserDetails.getMemberEntity();
        String role = memberEntity.getRole();
        String memberId = memberEntity.getMemberId();

        return new ResponseEntity<>(new LoginResultDto(accessToken, role, memberId),HttpStatus.OK);
    }



}