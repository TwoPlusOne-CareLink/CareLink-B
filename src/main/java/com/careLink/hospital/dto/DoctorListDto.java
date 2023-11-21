package com.careLink.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorListDto {
    private String doctorId;
    private String doctorName;
    private int departmentId;
    private String departmentName;
    private byte[] imgFile;
    private String doctorImg;

    public void base64Img(String base64Img) {
        doctorImg = base64Img;
    }

    public void imgFileReset() {
        imgFile = null;
    }

}