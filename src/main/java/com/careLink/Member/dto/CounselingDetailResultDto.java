package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingDetailResultDto {
    private String patientId;
    private int counseling_id;
    private String counselingTitle;
    private String counselingContent;
    private String counselingImage;
    private String counselingImageName;
    private String doctorImage;
    private String doctorImageName;
    private String doctorId;
    private String doctorName;
    private String departmentName;
    private int replyId;
    private String commentContent;
    private int likedByPatient;

    public void YesImageToResult(CounselingDetailDto counselingDetailDto, String counSelingBase64Img,String doctorBase64Img) {
        patientId=counselingDetailDto.getPatientId();
        counseling_id=counselingDetailDto.getCounseling_id();
        counselingTitle=counselingDetailDto.getCounselingTitle();
        counselingContent=counselingDetailDto.getCounselingContent();
        counselingImage= counSelingBase64Img;
        counselingImageName=counselingDetailDto.getCounselingImageName();
        doctorImage= doctorBase64Img;
        doctorImageName=counselingDetailDto.getDoctorImageName();
        doctorId=counselingDetailDto.getDoctorId();
        doctorName=counselingDetailDto.getDoctorName();
        departmentName=counselingDetailDto.getDepartmentName();
        replyId=counselingDetailDto.getReplyId();
        commentContent=counselingDetailDto.getCommentContent();
        likedByPatient=counselingDetailDto.getLikedByPatient();
    }
}

