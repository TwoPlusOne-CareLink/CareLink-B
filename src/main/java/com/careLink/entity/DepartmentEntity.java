package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    private int departmentId; //과목번호
    private String departmentName; //과목명
}