package com.careLink.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfoDto { //병원 예약 목록 정보
    private int reservationId; // 예약번호
    private String memberId; //회원 아이디
    private int hospitalId; //병원id
    private int departmentId; //진료과목id
    private Date reservationDate; //예약날짜
    private String reservationTime; //예약시간
    private String reservationMember; //예약자 이름
    private String reservationTel; //예약자 전화번호
    private String reservationContent; //예약내용
    private String departmentName; //예약한 진료 과목명
}