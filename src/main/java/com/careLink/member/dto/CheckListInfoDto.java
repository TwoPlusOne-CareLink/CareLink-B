package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckListInfoDto {
    private int checkId; //체크리스트 아이디
    private String memberId; // 회원아이디
    private String memberName; //회원 이름
    private String healthMemo; //내용
    private Date nowdate; //작성일
}