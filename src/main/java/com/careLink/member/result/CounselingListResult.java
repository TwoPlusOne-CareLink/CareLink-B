package com.careLink.member.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingListResult {
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private Map<String,Object> result;
}
