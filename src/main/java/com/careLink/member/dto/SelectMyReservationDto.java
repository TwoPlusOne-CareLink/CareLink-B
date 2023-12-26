package com.careLink.member.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class SelectMyReservationDto {
    private int reservationId; // 예약번호
    private String memberId; //회원 아이디
    private int hospitalId; //병원id
    private String hospitalName;//병원이름
    private int departmentId; //진료과목id
    private String departmentName;//진료과목이름
    private Date reservationDate; //예약날짜
    private String reservationTime; //예약시간
    private Date nowdate; //예약한날짜
    private String reservationMember; //예약자 이름
    private String reservationTel; //예약자 전화번호
    private String reservationContent; //예약내용
}