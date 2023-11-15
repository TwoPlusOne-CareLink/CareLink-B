package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInfoEntity {

    private int infoId; //의사정보 고유번호
    private String memberId; //의사회원 아이디(FK)
    private int departmentId; //진료과목 고유번호(FK)
    private int hospitalId; //병원고유번호(FK)
    private byte[] imgFile; //이미지 파일
    private String fileName; //파일명
    private MultipartFile file; //파일

    public DoctorInfoEntity(String memberId, int departmentId, int hospitalId, byte[] imgFile, String fileName) {
        this.memberId = memberId;
        this.departmentId = departmentId;
        this.hospitalId = hospitalId;
        this.imgFile = imgFile;
        this.fileName = fileName;
    }
}