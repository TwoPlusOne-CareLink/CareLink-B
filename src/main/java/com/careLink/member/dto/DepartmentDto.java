package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto { //진료과목
    private int departmentId; //과목번호
    private String departmentName; //과목명
}