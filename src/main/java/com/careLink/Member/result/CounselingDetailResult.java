package com.careLink.member.result;

import com.careLink.entity.CounselingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounselingDetailResult {
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private CounselingEntity counselingEntity;
}