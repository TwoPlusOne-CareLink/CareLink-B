package com.careLink.Member.service;

import com.careLink.Member.dto.GetRequestCounselingDto;
import com.careLink.Member.vo.MemberDto;

//회원가입&로그인, 마이페이지(회원정보수정), 비대면상담
public interface MemberService {
    public int signUp(MemberDto memberDto); //회원가입

    public GetRequestCounselingDto getCounselingMemberById(String id);
}