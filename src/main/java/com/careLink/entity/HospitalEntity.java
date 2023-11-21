package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//병원정보(병원찾기)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalEntity { //병원 테이블

    private int hospitalId; //병원 고유번호
    private String hospitalName; //병원 이름
    private String address; //병원 주소
    private String weekdayOpeningtime; //평일 영업시간
    private String weekendOpeningtime; //주말 영업시간
    private double lat; //위도
    private double lng; //경도
    private String tel; //전화번호
    private String holidayCheck; //주말&공휴일 운영여부
    private String lunchHour; //점심시간

}