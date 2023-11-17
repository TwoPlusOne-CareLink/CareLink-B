package com.careLink.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorMyCounselingDto {
    private int counselingId;
    private String counselingTitle;
    private String counselingContent;
    private byte[] counselingImage;
    private String counselingImageName;

}