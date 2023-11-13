package com.careLink.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwrUtil {

    //비밀키(누출되면 안됨)
    private static final String secretKey = "careLink";

    // 토큰 생성
    // JWT 토큰 생성: 사용자 아이디 포함
    public static String createToken(String id) {
        String token = null;
        try {
            JwtBuilder builder = Jwts.builder();
            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
            builder.setExpiration(new Date(new Date().getTime() + 1000*60*60*12)); //토큰의 유효기간(12시간)
            builder.claim("id", id); //토큰에 저장되는 데이터
            builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")); //비밀키
            token = builder.compact(); //모든 내용을 묶기
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //토큰생성 실패에 따른 예외처리
        }
        return token;
    }

    //JWT 토큰에서 모든 내용(Claims) 얻기
    public static Claims getClaims(String token) {
        Claims claims = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            claims = jws.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    //JWT 토큰에서 사용자 아이디 얻기
    public static String getId(String token) {
        String id = null;
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(secretKey.getBytes("UTF-8"));
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            id = claims.get("id", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
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
        } catch (Exception e) {
            e.printStackTrace();
            //토큰 유효기간 만료에 따른 예외처리
        }
        return validate;
    }

}