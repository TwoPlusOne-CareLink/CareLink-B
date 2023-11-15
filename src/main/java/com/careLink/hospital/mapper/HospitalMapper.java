package com.careLink.hospital.mapper;

import com.careLink.entity.DoctorInfoEntity;
import com.careLink.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

//병원 소속 의사 목록, 병원예약된 내역
@Mapper
public interface HospitalMapper {

    public void save(MemberEntity member); //회원 테이블에 의사 정보 저장
    public void upload(DoctorInfoEntity doctorInfo); //의사정보 테이블에 저장

}
