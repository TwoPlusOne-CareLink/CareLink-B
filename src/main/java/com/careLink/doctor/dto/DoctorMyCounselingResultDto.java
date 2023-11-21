package com.careLink.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorMyCounselingResultDto {
    private int counselingId;
    private String counselingTitle;
    private String counselingContent;
    private String counselingImage;
    private String counselingImageName;


    public void toResult(DoctorMyCounselingDto doctorMyCounselingDto, String base64Img) {
        counselingId = doctorMyCounselingDto.getCounselingId();
        counselingTitle = doctorMyCounselingDto.getCounselingTitle();
        counselingContent = doctorMyCounselingDto.getCounselingContent();
        counselingImage = base64Img;
        counselingImageName = doctorMyCounselingDto.getCounselingImageName();
    }
}