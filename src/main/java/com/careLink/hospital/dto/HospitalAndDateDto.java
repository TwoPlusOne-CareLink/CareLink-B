package com.careLink.hospital.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAndDateDto { //병원 아이디, 예약일 -> 해당날짜 예약 목록
    private int hospitalId; //병원아이디
    private Date reservationDate; //
}