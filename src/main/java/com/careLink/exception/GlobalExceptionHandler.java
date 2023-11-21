package com.careLink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> handleOnstagramException(ErrorException ex) {
        ErrorResponse response = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        // 에러 응답을 생성하여 클라이언트로 보냅니다.
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

}