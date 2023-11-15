package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestCounselingDto {
    private String memberId; //회원 아이디
    private String memberName; //회원 이름
}
