package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalInfoDto {
    private int hospitalId; //병원 고유번호
    private String hospitalName; //병원 이름
    private String address; //병원 주소
    private LatLngDto latlng; //위도 경도
    private String tel; //전화번호
}
