package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {

    private String memberId; //아이디
    private String password; //비밀번호
    private String memberName; //이름
    private String memberEmail; //이메일
    private String memberTel; //전화번호
    private String memberAddress; //주소
    private String memberAddressDetail; //상세주소
    private String role; //권한
    private int withdrawal; //회원 탈퇴 여부
    private int agree;//필수 동의 여부
    private Date nowdate; //가입 날짜
    private String age; //나이
    private String gender; //성별

    private int departmentId; //진료과목 고유번호(FK)
    private int hospitalId; //병원고유번호(FK)
    private byte[] imgFile; //이미지 파일
    private String fileName; //파일명
    private MultipartFile file; //파일

}