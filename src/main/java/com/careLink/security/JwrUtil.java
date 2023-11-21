package com.careLink.security;

import com.careLink.exception.ErrorException;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Log4j2
@Component
public class JwrUtil {

    //비밀키(누출되면 안됨)
    private static final String secretKey = "careLink";

    // 토큰 생성
    // JWT 토큰 생성: 사용자 아이디 포함
    public static String createToken(String memberId) {
        log.info("토큰생성 들어옴");
        String token = null;
        try {
            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
            builder.setExpiration(new Date(new Date().getTime() + 1000*60*60*12)); //토큰의 유효기간(12시간)
            builder.claim("memberId", memberId); //토큰에 저장되는 데이터
            builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")); //비밀키
            token = builder.compact(); //모든 내용을 묶기
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "토큰생성 실패");
            //토큰생성 실패에 따른 예외처리
        }

    }

    //JWT 토큰에서 모든 내용(Claims) 얻기
    public static Claims getClaims(String token) {
        Claims claims = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            claims = jws.getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "토큰에서 claims 얻기 실패");
        }
    }

    //JWT 토큰에서 사용자 아이디 얻기
    public static String getId(String token) {
        String id = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            id = claims.get("memberId", String.class);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "토큰에서 아이디 얻기 실패");
        }
    }

    //JWT 토큰 유효성 검사: 만료일자 확인
    public static boolean validateToken(String token) {
        boolean validate = false;

        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            validate = !claims.getExpiration().before(new Date());
			/*if(validate) {
				long remainMillSecs = claims.getExpiration().getTime() - new Date().getTime();
				logger.info("" + remainMillSecs/1000 + "초 남았음");
			}*/
            return validate;

        } catch (Exception e) {
            e.printStackTrace();
            //토큰 유효기간 만료에 따른 예외처리
            throw new ErrorException(HttpStatus.UNAUTHORIZED.value(),"토큰 만료");
        }
    }

}