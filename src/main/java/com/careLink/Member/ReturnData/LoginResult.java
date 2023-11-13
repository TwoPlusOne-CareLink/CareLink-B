package com.careLink.Member.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult { //로그인

    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private String token; //토큰
    private String role; //권한
}