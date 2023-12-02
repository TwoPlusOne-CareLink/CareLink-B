package com.careLink.member.controller;

import com.careLink.ResultDto;
import com.careLink.common.Common;
import com.careLink.entity.HealthCheckEntity;
import com.careLink.entity.ReservationEntity;
import com.careLink.member.dto.*;
import com.careLink.member.service.CareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//붱원찾기(예약), 일일체크
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class CareController {

    private final CareService careService;
    private final Common common;

    @GetMapping("/hospitalList") //병원 전체 목록 & 병원 검색
    public ResponseEntity<List<HospitalInfoDto>> hospitalList(@RequestParam String hospitalName) { //hospitalName: all(전체목록), 나머지: 검색 목록
        log.info("병원 찾기 입니다.");
        List<HospitalInfoDto> hospitalList = careService.hospitalInfo(hospitalName);
        return new ResponseEntity<>(hospitalList, HttpStatus.OK);
    }

    @GetMapping("/hospitalInfo/{hospitalId}") //병원 상세 정보
    public ResponseEntity<HospitalDetailResultDto> hospitalDetailResult(@PathVariable("hospitalId") int hospitalId) {
        HospitalDetailResultDto hospitalDetailResultDto = careService.hDetail(hospitalId); //병원아이디로 병원상세정보 조회
        return new ResponseEntity<>(hospitalDetailResultDto, HttpStatus.OK);
    }

    @GetMapping("/hospitalReservation/{hospitalId}") //병원 예약 페이지
    public ResponseEntity<ReservationPageDto> reservationPage(@PathVariable("hospitalId") int hospitalId) {
        String memberId = common.memberId();
        ReservationDto dto = new ReservationDto(memberId,hospitalId);
        ReservationPageDto reservationPageDto = careService.reservationPageInfo(dto);

        return new ResponseEntity<>(reservationPageDto, HttpStatus.OK);
    }

    @PostMapping("/hospitalReservation") //병원 예약
    public ResponseEntity<ResultDto> reservation(@RequestBody ReservationEntity reservationEntity) {
        String memberId = common.memberId(); //헤더에 담긴 토큰에서 아이디만 가져오는 메소드
        reservationEntity.memberId(memberId); //병원예약정보
        int reservationId = careService.Reservation(reservationEntity);

        return new ResponseEntity<>(new ResultDto(true,"예약 성공 예약번호 : " + reservationId), HttpStatus.OK);
    }

    @GetMapping("/getMyReservation")//나의 예약 전체 목록
    public ResponseEntity<List<SelectMyReservationDto>> getReservationList(){
        String memberId = common.memberId(); //헤더에 담긴 토큰에서 아이디만 가져오는 메소드
        List<SelectMyReservationDto> myReservation = careService.getMyReservation(memberId);
        return new ResponseEntity<>(myReservation, HttpStatus.OK);
    }

    @GetMapping("/getMyReservationDetail") // 나의 예약 전체 내역 & 해당일 예약 내역
    public ResponseEntity<SelectMyReservationDto> getMyReservationDetail(@RequestParam String reservationDate) {
        String memberId = common.memberId();
        MyReservationDetailDto dto = new MyReservationDetailDto(memberId, reservationDate);
        SelectMyReservationDto myReservation = careService.getMyReservationDetail(dto);
        return new ResponseEntity<>(myReservation, HttpStatus.OK);
    }

    @DeleteMapping("/hospitalReservationDelete/{reservationId}") //예약 취소
    public ResponseEntity<ResultDto> hospitalReservationDelete(@PathVariable int reservationId) {
        careService.reservationDelete(reservationId);
        return new ResponseEntity<>(new ResultDto(true,"예약취소 성공"), HttpStatus.OK);
    }

    @GetMapping("/checkList") //일일체크리스트 작성 & 작성목록
    public ResponseEntity<MemberInfoDto> checkList() {
        String memberId = common.memberId(); //헤더에 담긴 토큰에서 아이디만 가져오는 메소드
        MemberInfoDto dto = careService.HealthCheckListPage(memberId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/checkAdd") //일일체크리스트 작성
    public ResponseEntity<ResultDto> checkAdd(@RequestBody HealthCheckEntity healthCheckEntity) {
        String memberId = common.memberId(); //헤더에 담긴 토큰에서 아이디만 가져오는 메소드
        healthCheckEntity.setMemberId(memberId); //아이디 값 넣기
        int checkId = careService.HealthCheckAdd(healthCheckEntity);
        return new ResponseEntity<>(new ResultDto(true,"등록성공 작성번호 --> " + checkId), HttpStatus.OK);
    }

    @GetMapping("/checkListInfo") //일일체크리스트 작성 내역(결과)
    public ResponseEntity<CheckListResultDto> checkListInfo(@RequestParam int checkId) {
        CheckListResultDto dto = careService.ckResult(checkId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}