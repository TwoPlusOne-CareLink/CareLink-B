package com.careLink.member.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResult {
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
}
