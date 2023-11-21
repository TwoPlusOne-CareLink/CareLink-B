package com.careLink.hospital.mapper;

import com.careLink.hospital.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

//병원 소속 의사 목록, 병원예약된 내역
@Mapper
public interface HospitalMapper {

    public int hospitalId(String memberId);
    public List<DoctorListDto> doctorList(int hospitalId); //의사 목록
    public Optional<DoctorDetailDto> doctorDetail(String doctorId); //의사 상세 목록
    public Optional<CountDto> doctorCount(String doctorId); //좋아요, 답글수 카운트
    public List<ReservationInfoDto> reservationList(int hospitalId); //전체 예약 목록
    public List<ReservationInfoDto> reservationDateList(HospitalAndDateDto haDto); //예약일 예약 목록
    public Optional<ReservationInfoDto> reservationInfo(HospitalAndReservation haDto);//예약 상세 정보


}