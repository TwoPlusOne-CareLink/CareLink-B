package com.careLink.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {
    private String memberId; //로그인 아이디
    private String password; //로그인 비밀번호
}