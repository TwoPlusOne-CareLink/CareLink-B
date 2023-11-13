package com.careLink.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestCounselingDto {
    private String id; //회원 아이디
    private String memberName; //회원 이름
}
