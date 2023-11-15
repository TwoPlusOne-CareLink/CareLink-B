package com.careLink.member.mapper;

import com.careLink.entity.HospitalEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//붱원찾기(예약), 일일체크
@Mapper
public interface CareMapper {
    public List<HospitalEntity> hospitalSelect(); //병원 목록

    //병원 검색

    //병원 상세정보

    //병원 예약
}
