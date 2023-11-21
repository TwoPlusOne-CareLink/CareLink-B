package com.careLink.hospital.controller;

import com.careLink.common.Common;
import com.careLink.hospital.dto.*;
import com.careLink.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.sql.Date;
import java.util.List;


//병원 소속 의사 목록, 병원예약된 내역
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;
    private final Common common;
    @GetMapping("/doctorList") //의사 목록
    public ResponseEntity<List<DoctorListDto>> doctorList() {
        String hAdminId = common.memberId();//로그인한 회원의 아이디 받아오기
        int hospitalId = hospitalService.hospitalId(hAdminId);

        List<DoctorListDto> doctors = hospitalService.doctorList(hospitalId);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/reservationList") //해당 병원 전체 예약 목록
    public ResponseEntity<List<ReservationInfoDto>> reservationList() {
        String hAdminId = common.memberId();//로그인한 회원의 아이디 받아오기
        int hospitalId = hospitalService.hospitalId(hAdminId);

        List<ReservationInfoDto> rList = hospitalService.reservationList(hospitalId);
        return new ResponseEntity<>(rList,HttpStatus.OK);
    }

    @GetMapping("/reservationDateList") //해당 예약일 예약 목록
    public ResponseEntity<List<ReservationInfoDto>> reservationDateList(@RequestParam Date reservationDate) {
        String hAdminId = common.memberId();//로그인한 회원의 아이디 받아오기
        int hospitalId = hospitalService.hospitalId(hAdminId);

        HospitalAndDateDto haDto = new HospitalAndDateDto(hospitalId, reservationDate);

        List<ReservationInfoDto> rList = hospitalService.reservationDateList(haDto);

        return new ResponseEntity<>(rList, HttpStatus.OK);
    }

    @GetMapping("/reservationInfo") //해당 예약번호 예약 상세 정보
    public ResponseEntity<ReservationInfoDto> reservationInfo(@RequestParam int reservationId) {
        String hAdminId = common.memberId();//로그인한 회원의 아이디 받아오기
        int hospitalId = hospitalService.hospitalId(hAdminId);

        HospitalAndReservation haDto = new HospitalAndReservation(hospitalId, reservationId);
        ReservationInfoDto resultDto = hospitalService.reservationInfo(haDto);

        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @GetMapping("/doctorDetail")
    public ResponseEntity<DoctorDetailDto> doctorDetail(@RequestParam String doctorId) {
        log.info("의사 상세 정보");
        DoctorDetailDto dto = hospitalService.doctorDetail(doctorId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}