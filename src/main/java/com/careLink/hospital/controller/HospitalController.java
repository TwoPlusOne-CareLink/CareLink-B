package com.careLink.hospital.controller;

import com.careLink.entity.DoctorEntity;
import com.careLink.hospital.result.DoctorJoinResult;
import com.careLink.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


//병원 소속 의사 목록, 병원예약된 내역
@RestController
@RequiredArgsConstructor
@Log4j2
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping(value = "/doctorAdd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//의사 등록
    public DoctorJoinResult doctorAdd(@RequestPart MultipartFile file, DoctorEntity doctor) {
        log.info("의사등록 : " + doctor.getMemberName());
        int memberNo= hospitalService.signup(doctor);
        return new DoctorJoinResult(HttpStatus.OK.value(), true, memberNo);
    }
}
