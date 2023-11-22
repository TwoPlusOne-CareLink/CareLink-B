package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto { //일일체크리스트 작성 폼 회원 정보
    private String memberId; //아이디
    private String memberName; //이름
    private int age; //나이
    private String gender; //성별
    private List<CheckListInfoDto> checkListInfoDtoList; //체크리스트 항목 정보
}