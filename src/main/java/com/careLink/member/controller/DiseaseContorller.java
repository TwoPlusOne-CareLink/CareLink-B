package com.careLink.member.controller;

import com.careLink.entity.DiseaseEntity;
import com.careLink.member.service.CareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class DiseaseContorller {

    private final CareService careService;
    @GetMapping("/diseaseList") //질병 백과
    public ResponseEntity<List<DiseaseEntity>> diseaseList() {
        List<DiseaseEntity> dList = careService.diseaseList();
        return new ResponseEntity<>(dList, HttpStatus.OK);
    }

    @GetMapping("/diseaseDetail") // 질병 상세 정보
    public ResponseEntity<DiseaseEntity> diseaseDetail(@RequestParam int diseaseId) {
        DiseaseEntity dto  = careService.diseaseDetail(diseaseId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
