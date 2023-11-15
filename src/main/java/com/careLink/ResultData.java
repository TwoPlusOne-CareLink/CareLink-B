package com.careLink;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResultData {
    private int ErrorCode;
    private boolean success;
    private Object data;
}