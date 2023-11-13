package com.careLink.Member.controller;

import com.careLink.Member.dto.GetRequestCounselingDto;
import com.careLink.Member.result.GetRequestCounselingResult;
import com.careLink.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    public final MemberService memberService;

    @GetMapping("/requestCounseling") //비대면상담신청(회원) 아이디,이름
    public GetRequestCounselingResult getRequestCounselingDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        GetRequestCounselingDto Dto = memberService.getCounselingMemberById(id);


        return new GetRequestCounselingResult(HttpStatus.OK.value(), true, Dto);
    }



}
