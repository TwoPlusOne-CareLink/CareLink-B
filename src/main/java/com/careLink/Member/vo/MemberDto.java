package com.careLink.Member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    
    private int memberId; //회원 고유번호
    private String id; //아이디
    private String password; //비밀번호
    private String memberName; //이름
    private String memberEmail; //이메일
    private String memberTel; //전화번호
    private String memberAddress; //주소
    private String role; //권한
    private int withdrawal; //회원 탈퇴 여부
    private int agree;//필수 동의 여부
    private Date nowdate; //가입 날짜
    private String age; //나이
    private String gender; //성별

    //회원가입시 역할,회원탈퇴여부 기본값주고 db 전송
    public void join(String memberRole, int memberWithdrawal) {
        role = memberRole;
        withdrawal = memberWithdrawal;
    }

    //로그인, 회원정보 수정시 기존에 비밀번호 확인용도
    public void passwordEncoder(String encoderPassword) {
        password = encoderPassword;
    }

}