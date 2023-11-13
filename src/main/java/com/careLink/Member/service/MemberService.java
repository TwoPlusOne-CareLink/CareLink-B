package com.careLink.Member.service;

import com.careLink.Member.ReturnData.LoginResult;
import com.careLink.Member.domain.MemberDto;
import com.careLink.Member.domain.SignInDto;

//회원가입&로그인, 마이페이지(회원정보수정), 비대면상담
public interface MemberService {
    public int signUp(MemberDto memberDto); //회원가입
//    public LoginResult signIn(SignInDto signInDto); //로그인

}