package com.careLink.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorListDto {
    private String doctorId; //의사 아이디
    private String doctorName; //의사이름
    private int departmentId; //진료과목 아이디
    private String departmentName; //진료과목명
    private int likeCount; //좋아요수
}