package com.careLink.Member.controller;

import com.careLink.Member.domain.HospitalDto;
import com.careLink.Member.ReturnData.HospitalListResult;
import com.careLink.Member.service.CareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

//붱원찾기(예약), 일일체크
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class CareController {

    private final CareService careService;
    @GetMapping("/hospitalList") //병원 목록
    public HospitalListResult hospitalList() {

        List<HospitalDto> hospitalDtoList = careService.hospitalInfo(); //병원 전체 목록

        return new HospitalListResult(HttpStatus.OK.value(), true, hospitalDtoList);
    }

    //병원 검색

    //병원 상세 정보

    //병원 예약
}
