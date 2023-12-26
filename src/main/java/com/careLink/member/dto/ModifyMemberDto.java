package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMemberDto {

    private String memberId;
    private String password;
    private String memberName;
    private String memberEmail;
    private String memberAddress;
    private String memberAddressDetail;
    private String memberTel;

    public void passwordEncode(String encodePw){
        password = encodePw;

    }
}