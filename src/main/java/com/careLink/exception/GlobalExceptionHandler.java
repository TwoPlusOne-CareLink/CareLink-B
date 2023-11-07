package com.careLink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<String> handleOnstagramException(ErrorException ex) {
        // 에러 응답을 생성하여 클라이언트로 보냅니다.
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.valueOf(ex.getErrorCode()));
    }

}