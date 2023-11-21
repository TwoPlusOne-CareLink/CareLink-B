package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPageInfoDto { //병원 예약페이지 -> 병원 진료 예약 정보
    private int reservationId; //에약 번호
    private Date reservationDate; //예약 날짜
    private String reservationTime; //진료시간
    private String departmentName; //진료 과목명
}