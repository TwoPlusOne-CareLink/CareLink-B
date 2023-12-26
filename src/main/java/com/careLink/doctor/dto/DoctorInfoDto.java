package com.careLink.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfoDto { //성민
    private int infoId; //의사정보 고유번호
    private String doctorId; //의사회원 아이디(FK)
    private int departmentId; //진료과목 고유번호(FK)
    private int hospitalId; //병원고유번호(FK)
    private byte[] doctorImg; //이미지 파일
    private String fileName; //파일명
    private MultipartFile file; //파일

    public DoctorInfoDto(String doctorId, int departmentId, int hospitalId, byte[] doctorImg, String fileName) {
        this.doctorId = doctorId;
        this.departmentId = departmentId;
        this.hospitalId = hospitalId;
        this.doctorImg = doctorImg;
        this.fileName = fileName;
    }

}