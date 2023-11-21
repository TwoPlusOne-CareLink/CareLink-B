package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateCheckDto { //체크리스트 중복 검사용 Dto
    private String memberId; //회원아이디
    private String nowdate; //작성일
}