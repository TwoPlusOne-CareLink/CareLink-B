package com.careLink.member.mapper;

import com.careLink.entity.HealthCheckEntity;
import com.careLink.entity.HospitalEntity;
import com.careLink.entity.ReservationEntity;
import com.careLink.member.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

//붱원찾기(예약), 일일체크
@Mapper
public interface CareMapper {
    public List<HospitalEntity> hospitalSelect(String hospitalName); //병원 목록 & 검색

    //병원 상세정보(1)
    public HospitalDetailDto hospitalInfo(int hospitalId); //병원정보(+진료과목)
    public List<DoctorProfileDto> doctorProfile(int hospitalId); //병원에 속한 의사들의 정보
    public int hospitalLikeCount(int hospitalId); //병원 좋아요 개수

    //병원예약
    public int dateCheck(ReservationEntity reservationEntity); //당일 다른 예약이 있는지 중복확인(1일 1예약 원칙)
    public int check(ReservationEntity reservationEntity);//병원 중복 예약 확인
    public void reservation(ReservationEntity reservationEntity); //병원 예약 하기

    //병원 예약 페이지
    public Optional<ReservationDefaultDto> rDefaultInfo(ReservationDto reservationDto); //회원정보, 진료과목 정보
    public List<ReservationPageInfoDto> dateInfo (int hospitalId); //병원 에약 정보
    public List<SelectMyReservationDto> selectMyReservation(@Param("memberId") String memberId);//나의 예약 전체 목록
    public Optional<SelectMyReservationDto> getMyReservationDetail(MyReservationDetailDto myReservationDetailDto); //예약일 상세 내역
    public void reservationDelete(int reservationId); //예약 취소

    //일일체크리스트
    public List<CheckListInfoDto> checkListPage(String memberId);//일일체크리스트 작성 & 목록 페이지
    public void checkListAdd(HealthCheckEntity healthCheckEntity); //체크리스트 작성 등록
    public int duplicateCheck(DuplicateCheckDto dto); //체크리스트 당일 중복 작성 방지
    public Optional<CheckListResultDto> checkResult(int checkId); //체크리스트 작성내역
    
    //질병백과
    public List<DiseaseDto> diseaseList(); //질병백과

}
