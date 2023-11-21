package com.careLink.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDto { //로그인
    private String token; //토큰
    private String role; //권한
    private String memberId;//아이디
}