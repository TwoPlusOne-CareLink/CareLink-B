package com.careLink.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {

    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private MemberDto memberDto; //회원정보
    private String token; //토큰
}