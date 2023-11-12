package com.careLink.Member.ReturnData;

import com.careLink.Member.domain.HospitalDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalListResult { //병원 목록

    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private List<HospitalDto> hospitalList; // 병원 목록

}