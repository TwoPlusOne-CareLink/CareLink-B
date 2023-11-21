package com.careLink.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDetailResultDto { //병원 상세정보(리턴해주는 dto)
    private int hospitalId; //병원 고유번호
    private String hospitalName; //병원 이름
    private String address; //병원 주소
    private String weekdayOpeningtime; //평일 영업시간
    private String weekendOpeningtime; //주말 영업시간
    private String tel; //전화번호
    private String holidayCheck; //주말&공휴일 운영여부
    private String lunchHour; //점심시간
    private LatLngDto latlng; //위도 경오 있는 객체
    private int totalLike; //병원에 속한 의사 좋아요 총 개수
    private List<String> departmentNames; //병원에서 진료하는 진료과목들(DB에서 읽어오고 split(',')해워서 배열로 새로 저장해야함
    private List<DoctorProfileResultDto> doctorInfo; //의사 정보들
}