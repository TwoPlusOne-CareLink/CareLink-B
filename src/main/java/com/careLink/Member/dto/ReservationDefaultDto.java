package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDefaultDto {
    private String memberName;
    private String memberTel;
    private String departmentIds;
    private String DepartmentNames;
}
