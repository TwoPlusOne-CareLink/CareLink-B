package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileDto { //의사아이디, 종아요 개수(병원 상세페이지에서 사용) -> 병원 좋아요 개수 구할때 사용
    private String memberId; //의사 아이디
    private String memberName; //의사 이름
    private int departmentId; //의사가 담당하는 진료과 번호
    private String departmentName; //진료명
    private byte[] imgFile; //의사 사진
    private String doctorImg; //의사 사진 byte[] -> base64
    private String fileName; //의사 사진 파일명

    public void base64Img(String base64Img) {
        doctorImg = base64Img;
        imgFile = null;
    }

}