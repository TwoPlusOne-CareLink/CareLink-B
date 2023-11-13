package com.careLink.Member.result;

import com.careLink.Member.dto.GetRequestCounselingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestCounselingResult {
    private int errorCode; //에러코드
    private boolean success; //작업성공여부
    private GetRequestCounselingDto getRequestCounselingDto;
}
