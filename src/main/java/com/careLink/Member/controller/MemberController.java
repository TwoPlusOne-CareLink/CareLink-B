package com.careLink.Member.controller;

import com.careLink.Member.domain.MemberDto;
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

        return (memberId == -1) ? new ResultDto(HttpStatus.NOT_FOUND.value(), false, null)
                                : new ResultDto(HttpStatus.OK.value(), true, null);
    }

}
