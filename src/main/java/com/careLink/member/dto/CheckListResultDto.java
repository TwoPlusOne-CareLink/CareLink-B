package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckListResultDto {
    private int checkId; //체크리스트 번호
    private String memberId; //회원아이디
    private String memberName; //회원이름
    private String gender; //성별
    private int age; //나이
    private double height; //신장
    private double weight; //체중
    private int heartRate; //심박수
    private int bloodSugar; //혈당지수
    private int systolicBloodPressure; //수축기 혈압
    private int relaxationBloodPressure; //이완혈압
    private String healthMemo; //내용
    
    private String bpResult; //혈압 결과
    private String bsResult; //심받수 결과
    private String hrResult; //혈당 결과
    private String bmi;

    public void result(String bp, String bs, String hr, String BMI) {
        bpResult = bp;
        bsResult = bs;
        hrResult = hr;
        bmi = BMI;
    }
}