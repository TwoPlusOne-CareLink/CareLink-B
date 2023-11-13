package com.careLink.Member.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinResult { //회원 가입
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
}