package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckEntity { //체크리스트 테이블
    private int checkId; //체크리스트 번호
    private String memberId; //회원아이디
    private String memberName; //회원이름
    private String gender; //성별
    private int age; //나이
    private double height; // 신장
    private double weight; // 체충
    private int systolicBloodPressure; //수축기 혈압
    private int relaxationBloodPressure; //이완 혈압
    private int bloodSugar; //혈당
    private int heartRate; //심박수
    private String healthMemo; //내용
    private Date nowdate; //작성일

    public void setMemberId(String id) {
        memberId = id;
    }
}