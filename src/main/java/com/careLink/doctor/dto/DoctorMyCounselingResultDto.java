package com.careLink.doctor.dto;

import com.careLink.entity.CounselingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
