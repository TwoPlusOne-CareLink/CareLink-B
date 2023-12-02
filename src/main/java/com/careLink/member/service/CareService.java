package com.careLink.member.service;

import com.careLink.entity.DiseaseEntity;
import com.careLink.entity.HealthCheckEntity;
import com.careLink.entity.ReservationEntity;
import com.careLink.member.dto.*;

import java.util.List;

//붱원찾기(예약), 일일체크
public interface CareService {

    //병원 찾기
    public List<HospitalInfoDto> hospitalInfo(String hospitalName); //병원 전체 목록 & 정보 && 검색
    public HospitalDetailResultDto hDetail(int hospitalId);//병원 상세 목록

    //병원 찾기 - 예약
    public ReservationPageDto reservationPageInfo(ReservationDto dto); //병원 예약 페이지
    public int Reservation(ReservationEntity reservationEntity); //병원 예약
    public List<SelectMyReservationDto> getMyReservation(String memberId); //나의 예약 전체 목록
    public SelectMyReservationDto getMyReservationDetail(MyReservationDetailDto myReservationDetailDto); //나의 예약 상세 내역



    public void reservationDelete(int reservationId); //예약 취소

    //건강체크
    public MemberInfoDto HealthCheckListPage(String memberId); //체크리스트 작성 & 목록 페이지
    public int HealthCheckAdd(HealthCheckEntity healthCheckEntity); //체크리스트 작성
    public CheckListResultDto ckResult(int checkId); // 체크리스트 작성 내역(결과)

    //질병백과
    public List<DiseaseEntity> diseaseList(); //질병 백과 목록
    public DiseaseEntity diseaseDetail(int diseaseId); //질병 상세 정보

}
