package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPageDto { //병원 예약  -> 최종 리턴값
    private String memberName; //예약자 이름
    private String memberTel; //예약자 전화번호
    private List<DepartmentDto> departments; // 해당 병원 진료과목 정보
    private List<ReservationPageInfoDto> reservations; //해당병원 예약 정보
}