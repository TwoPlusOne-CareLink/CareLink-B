package com.careLink.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailDto {
    private byte[] imgFile; //사진
    private String doctorImg; //의사사진
    private String doctorId; //아이디
    private String doctorName; //이름
    private String doctorTel; //전화번호
    private String departmentName; //진료과목
    private String hospitalName; //소속병원
    private int counselingCount;//상담개수
    private int likeCount; //좋아요 개수

    public void count(String base64Img, int counseling, int like) {
        imgFile = null;
        doctorImg = base64Img;
        counselingCount = counseling;
        likeCount = like;
    }
}