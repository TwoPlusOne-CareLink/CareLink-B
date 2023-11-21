package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class hospitalDepartmentEntity { //진료과목-병원 정보 테이블
    private int hospitalDepartment;
    private int hospitalId;
    private int departmentId;
}