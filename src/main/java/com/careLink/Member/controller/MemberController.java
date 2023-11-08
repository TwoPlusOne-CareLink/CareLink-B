package com.careLink.Member.controller;

import com.careLink.Member.domain.LoginResult;
import com.careLink.Member.domain.MemberDto;
import com.careLink.Member.domain.SignInDto;
import com.careLink.Member.service.MemberService;
import com.careLink.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//회원가입&로그인, 마이페이지(회원정보수정), 비대면상담
@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup") //회원가입
    public ResultDto signup(@RequestBody MemberDto memberDto) {

        memberDto.join("ROLE_USER",1); //회원권한, 회원탈퇴여부 부여
        int memberId = memberService.signUp(memberDto); //회원가입 성공 후 고유 번호 리턴

        return (memberId == -1) ? new ResultDto(HttpStatus.UNAUTHORIZED.value(), false, null) //401 상태코드
                                : new ResultDto(HttpStatus.OK.value(), true, null);
    }

    @PostMapping("/login") //로그인 -> 토큰 추가하고 service 리턴 타입 String로 변경예정
    public LoginResult login(@RequestBody SignInDto signInDto) {
        return memberService.signIn(signInDto);
    }


}
