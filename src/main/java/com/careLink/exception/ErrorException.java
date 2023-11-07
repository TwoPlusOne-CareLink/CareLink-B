package com.careLink.exception;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public class ErrorException extends RuntimeException {

    private int ErrorCode;
    private String ErrorMessage;

    public ErrorException() {
        super();
    }
    public ErrorException(int ErrorCode, String ErrorMessage) {
        this.ErrorCode = ErrorCode;
        this.ErrorMessage = ErrorMessage;
    }

}
