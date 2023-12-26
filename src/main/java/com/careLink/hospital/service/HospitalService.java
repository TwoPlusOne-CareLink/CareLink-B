package com.careLink.hospital.service;

import com.careLink.hospital.dto.*;

import java.util.List;

//병원 소속 의사 목록, 병원예약된 내역
public interface HospitalService {

    public int hospitalId(String hAdminId); //소속 병원 아이디값 가져오기
    public List<DoctorListDto> doctorList(int hospitalId); //의사 목록
    public List<ReservationInfoDto> reservationList(int hospitalId); //전체 예약 목록
    public List<ReservationInfoDto> reservationDateList(HospitalAndDateDto haDto); //해당 날짜 예약 목록
    public ReservationInfoDto reservationInfo(HospitalAndReservation haDto); //예약 상세 정보
    public DoctorDetailDto doctorDetail(String doctorId); //의사 상세 정보
}