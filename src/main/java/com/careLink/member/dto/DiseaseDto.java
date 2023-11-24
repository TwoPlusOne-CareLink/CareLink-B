package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseDto {
    private int diseaseId; //질병 원인
    private String diseaseName;  //질병이름
    private String definition;  //정의
    private String cause; //원인
    private String symptom; //증상
    private String diagnosis; //진단
}