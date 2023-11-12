package com.careLink.Member.controller;

import com.careLink.Member.ReturnData.JoinResult;
import com.careLink.Member.ReturnData.LoginResult;
import com.careLink.Member.domain.MemberDto;
import com.careLink.Member.domain.SignInDto;
import com.careLink.Member.service.MemberService;
import com.careLink.security.JwrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//회원가입&로그인, 마이페이지(회원정보수정), 비대면상담
@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;
    private final JwrUtil jwrUtil;

    @PostMapping("/signup") //회원가입
    public JoinResult signup(@RequestBody MemberDto memberDto) {

        memberDto.join("ROLE_USER",1); //회원권한, 회원탈퇴여부 부여
        int memberId = memberService.signUp(memberDto); //회원가입 성공 후 고유 번호 리턴

        return (memberId == -1) ? new JoinResult(HttpStatus.UNAUTHORIZED.value(), false) //401 상태코드(아이디 중복)
                : new JoinResult(HttpStatus.OK.value(), true);
    }

    @PostMapping("/login") //로그인 -> 토큰 추가하고 service 리턴 타입 String로 변경예정
    public LoginResult login(@RequestBody SignInDto signInDto) {

        //아이디와 패스워드 검증-------------------------------------
        //아이디와 비밀번호를 가지고 있는 객체 생성
        log.info("로그인 시작");
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(signInDto.getId(), signInDto.getPassword());
        log.info("2");

        //DB에서 사용자 정보와 일치하는지 검사해서(비밀번호 체킹)
        //맞을 경우 Authentication 객체 리턴
        //맞지 않을 경우 403 예외 발생
        Authentication authentication = authenticationManager.authenticate(authToken); // -----> 입력한 비밀번호와 로그인한 아이디의 회원정보에서 비밀번호와 체킹
        log.info("3");
        //Spring Security에 인증 객체를 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("4");

        //AccessToken 생성 ---------------------------------------
        String accessToken = jwrUtil.createToken(signInDto.getId());
        log.info("토큰 : " + accessToken);
        return new LoginResult(HttpStatus.OK.value(), true, accessToken);
    }

}