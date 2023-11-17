package com.careLink.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
public class Common {
    public String convertImageToBase64(byte[] file) {
        log.info("base64 변환 시작");
        return Base64.getEncoder().encodeToString(file);
    }
    public String memberId() {
        log.info("회원 아이디 토큰에서 얻기");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}