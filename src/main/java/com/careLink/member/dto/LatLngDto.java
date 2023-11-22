package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LatLngDto {
    private double lat; //위도
    private double lng; //경도
}
