package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDetailDto { //병원상세정보를 조인해서 db에서 일단 받는 dto
    private int hospitalId; //병원 고유번호
    private String hospitalName; //병원 이름
    private String address; //병원 주소
    private String weekdayOpeningtime; //평일 영업시간
    private String weekendOpeningtime; //주말 영업시간
    private String tel; //전화번호
    private String holidayCheck; //주말&공휴일 운영여부
    private String lunchHour; //점심시간
    private double lat; //위도
    private double lng; //경도
    private String departmentNames; //병원에서 진료하는 진료과목들(DB에서 읽어오고 split(',')해워서 배열로 새로 저장해야함
}
