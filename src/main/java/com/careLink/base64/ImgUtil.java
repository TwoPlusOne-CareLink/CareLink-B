package com.careLink.base64;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;

//이미지(blob)를 byte로 받아와서 base64로 변환
@Component
@Slf4j
public class ImgUtil {
    public String convertImageToBase64(byte[] file) {
        log.info("base64 변환 시작");
        return Base64.getEncoder().encodeToString(file);
    }
}