package com.careLink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private Object data; //보내줄 데이터
}
