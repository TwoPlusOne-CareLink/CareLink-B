package com.careLink.doctor.controller;


import com.careLink.ResultDto;
import com.careLink.doctor.dto.DoctorDto;
import com.careLink.doctor.service.DoctorService;
import com.careLink.entity.DoctorInfoEntity;
import com.careLink.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DoctorAddController {

    private final DoctorService doctorService;
    @PostMapping(value = "doctorAdd", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//의사회원가입(성민)
    public ResponseEntity<ResultDto> doctorAdd(@RequestPart MultipartFile file, DoctorDto doctor) {

        //의사 정보 테이블
        DoctorInfoEntity doctorinfo;

        try {
            if (file != null && ! file.isEmpty()) { //파일이 존재할 떄
                String fileName = file.getOriginalFilename();
                doctorinfo = new DoctorInfoEntity(doctor.getDoctorId(), doctor.getDepartmentId(), doctor.getHospitalId(), file.getBytes(), fileName);
            } else {
                doctorinfo = new DoctorInfoEntity(doctor.getDoctorId(), doctor.getDepartmentId(), doctor.getHospitalId(), null, null);
            }
        } catch (Exception e) {
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "사진파일 변환 실패");
        }

        String memberId = doctorService.signup(doctor, doctorinfo);
        return new ResponseEntity<>(new ResultDto(true, memberId + " 의사등록 성공"), HttpStatus.OK);

    }
}
