package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingDetailDto {
    private String patientId;
    private int counselingId;
    private String counselingTitle;
    private String counselingContent;
    private byte[] counselingImage;
    private String counselingImageName;
    private byte[] doctorImage;
    private String doctorImageName;
    private String doctorId;
    private String doctorName;
    private String departmentName;
    private int replyId;
    private String commentContent;
    private int likedByPatient;
}