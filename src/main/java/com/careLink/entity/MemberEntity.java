package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    
    private int memberNo; //회원 고유번호
    private String memberId; //아이디
    private String password; //비밀번호
    private String memberName; //이름
    private String memberEmail; //이메일
    private String memberTel; //전화번호
    private String memberAddress; //주소
    private String memberAddressDetail; //주소
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

    //의사 회원가입시 회원정보 담기
    public MemberEntity(String memberId, String password, String memberName, String memberEmail, String memberTel, String memberAddress, String memberAddressDetail, String role, int withdrawal, int agree, String age, String gender) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberTel = memberTel;
        this.memberAddress = memberAddress;
        this.memberAddressDetail = memberAddressDetail;
        this.role = role;
        this.withdrawal = withdrawal;
        this.agree = agree;
        this.age = age;
        this.gender = gender;
    }

}