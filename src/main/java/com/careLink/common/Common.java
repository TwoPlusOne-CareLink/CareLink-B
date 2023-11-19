package com.careLink.common;

import com.careLink.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class Common {
    public String convertImageToBase64(byte[] file) {
        try{
        log.info("base64 변환 시작");
        return Base64.getEncoder().encodeToString(file);

        }catch (Exception e){
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "base64 변환 실패");
        }
    }
    public String memberId() {
        try{
        log.info("회원 아이디 토큰에서 얻기");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

        } catch (Exception e){
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "토큰에서 아이디 받아오기 실패");
        }
    }
}