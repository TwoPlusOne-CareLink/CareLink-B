package com.careLink.doctor.controller;

import com.careLink.common.Common;
import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.doctor.service.DoctorService;
import com.careLink.entity.CounselingPager;
import com.careLink.member.dto.CounselingResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final Common common;

    @GetMapping("/counselingList") //의사의 부서에 따른 답글이 안달린 상담 목록
    public ResponseEntity<List> list(@RequestParam(defaultValue = "1") int pageNo) {
        String id = common.memberId();
        int doctorDepartmentId = doctorService.getDepartmentId(id);
        log.info("=================="+id+"==================="+doctorDepartmentId);

        int totalRows = doctorService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 5, totalRows, pageNo);

        List<DoctorCounselingListDto> list = doctorService.doctorGetList(counselingPager, doctorDepartmentId);

//      리스트만 보내줘도됨
//        Map<String, Object> map = new HashMap<>();
//        map.put("list", list);
//        map.put("pager", counselingPager);
        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/myCounselingResult") // 자신이 댓글단 상담 목록 받아 오기
    public ResponseEntity<List> getMyResult(@RequestParam(defaultValue = "1") int pageNo){
        String id = common.memberId();

        int totalRows = doctorService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 8, totalRows, pageNo);

        List<DoctorMyCounselingResultDto> list = doctorService.doctorGetMyCounseling(counselingPager, id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

