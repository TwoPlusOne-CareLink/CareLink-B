package com.careLink.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalAndReservation {
    private int hospitalId; //병원 아이디
    private int reservationId; //예약번호
}