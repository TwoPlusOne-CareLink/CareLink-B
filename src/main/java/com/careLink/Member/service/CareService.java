package com.careLink.Member.service;

import com.careLink.Member.vo.HospitalDto;

import java.util.List;

//붱원찾기(예약), 일일체크
public interface CareService {

    public List<HospitalDto> hospitalInfo(); //병원 전체 목록 & 정보
    //병원 검색
    //병원 상세 목록
    //병원 예약
}
