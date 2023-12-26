package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity { //예약 테이블
    private int reservationId; // 예약번호
    private String memberId; //회원 아이디
    private int hospitalId; //병원id
    private int departmentId; //진료과목id
    private Date reservationDate; //예약날짜
    private String reservationTime; //예약시간
    private Date nowdate; //예약한날짜
    private String reservationMember; //예약자 이름
    private String reservationTel; //예약자 전화번호
    private String reservationContent; //예약내용

    public void memberId(String id) {
        memberId = id;
    }

}