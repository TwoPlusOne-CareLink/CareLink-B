package com.careLink.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
@Slf4j
public class Common {
    public String convertImageToBase64(byte[] file) {
        log.info("base64 변환 시작");
        return Base64.getEncoder().encodeToString(file);
    }

    public String memberId() { //토큰에서 회원아이디 얻기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public int age(int gender, String age) { //나이계산
        age = gender < 2 ? "19"+age : "20" + age;

        // 주민등록번호에서 년도와 월 추출
        int birthYear = Integer.parseInt(age.substring(0, 4)); //1900년도
        int birthMonth = Integer.parseInt(age.substring(4, 6)); //01월
        int birthDay = Integer.parseInt(age.substring(6, 8)); //01일

        // 현재 연도를 4자리로 구하기
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        int year = Integer.parseInt(currentYear);


        // 만 나이 계산
        int memberAge = year - birthYear; //현재년도 - 생년월일 년도

        int nowMonth = LocalDate.now().getMonthValue();
        int nowDate = LocalDate.now().getDayOfMonth();

        // 생일이 지났는지 체크하여 만 나이 조정
        if (nowMonth == birthMonth) { //주민번호상 날짜가 같을 경우
            memberAge -= nowDate >= birthDay ? 0 : 1; //생일이 안지났으면 나이 -1
        }
        if(nowMonth < birthMonth) { //생일이 지났을 경우
            memberAge--;
        }
        return memberAge;
    }

    public String bloodPressure(int systolic, int relaxation) { //혈압 체크
        if (systolic < 120 && relaxation < 80) {
            return "정상";
        } else if ((systolic >= 120 && systolic < 130) && relaxation < 80 ) {
            return "주의";
        } else if ((systolic >= 130 && systolic < 140) && relaxation >= 80 && relaxation < 90) {
            return "고혈압 전단계";
        } else if ((systolic >= 140 && systolic < 160) || (relaxation >= 90 && relaxation < 100)) {
            return "고혈압 1기";
        } else if (systolic >= 160 || relaxation >= 100) {
            return "고혈압 2기";
        } else if(systolic >= 140 && relaxation < 90) {
            return "수축기 단독 고혈압";
        } else {
            return "기타(다시 측정해주세요.)";
        }

    }

    public String bmiResult(double bmi) { //bmi 결과
        String result;
        if(bmi < 18.5) { result="저체중";}
        else if(18.5<=bmi && bmi <23) {result = "정상";}
        else if(23 <= bmi && bmi < 25) {result = "과체중(비만전단계)";}
        else if(25 <= bmi && bmi < 30) {result = "비만(1단계)";}
        else if(30<=bmi && bmi <35) {result = "비만(2단계)";}
        else { result = "고도비만(3단계)";} //35이상
        return result;
    }

    public String bsResult(int bloodSugar) { //혈당 결과
        String result;
        if(70<=bloodSugar && bloodSugar<=99) {
            result = "정상";
        } else if(100<=bloodSugar && bloodSugar<=125) {
            result = "전단계";
        } else if(126<=bloodSugar) {
            result = "당뇨";
        } else {
            result = "오류(잘못입력)";
        }
        return result;
    }

    public String hrResult(String gender, int age, int heartRate) {
        String result;
        if(gender.equals("남자")) { //남성
            result = mResult(age,heartRate);
        }
        else { //여성
            result = gResult(age,heartRate);
        }
        return result;
    }

    public String mResult(int age, int heartRate) { //남자

        String result;

        if(18<=heartRate && heartRate<=25) {
            result = 70<=heartRate&&heartRate<=73 ? "정상" : (heartRate<70 ? "낮음" : "높음");
        } else if(26<=heartRate && heartRate<=35) {
            result = 71<=heartRate&&heartRate<=74 ? "정상" : (heartRate<71 ? "낮음" : "높음");
        }
        else if(36<=heartRate && heartRate<=45) {
            result = 71<=heartRate&&heartRate<=75 ? "정상" : (heartRate<71 ? "낮음" : "높음");
        }
        else if(46<=heartRate && heartRate<=55) {
            result = 72<=heartRate&&heartRate<=76 ? "정상" : (heartRate<72 ? "낮음" : "높음");
        }
        else if(56<=heartRate && heartRate<=65) {
            result = 72<=heartRate&&heartRate<=75 ? "정상" : (heartRate<72 ? "낮음" : "높음");
        }
        else if(66<heartRate) {
            result = 70<=heartRate&&heartRate<=73 ? "정상" : (heartRate<70 ? "낮음" : "높음");
        } else {
            result = "오류(잘못입력)";
        }

        return result;
    }

    public String gResult(int age, int heartRate) { //여자
        String result;

        if(18<=heartRate && heartRate<=25) {
            result = 74<=heartRate&&heartRate<=78 ? "정상" : (heartRate<74 ? "낮음" : "높음");
        } else if(26<=heartRate && heartRate<=35) {
            result = 73<=heartRate&&heartRate<=76 ? "정상" : (heartRate<73 ? "낮음" : "높음");
        }
        else if(36<=heartRate && heartRate<=45) {
            result = 74<=heartRate&&heartRate<=78 ? "정상" : (heartRate<74 ? "낮음" : "높음");
        }
        else if(46<=heartRate && heartRate<=55) {
            result = 74<=heartRate&&heartRate<=77 ? "정상" : (heartRate<74 ? "낮음" : "높음");
        }
        else if(56<=heartRate && heartRate<=65) {
            result = 74<=heartRate&&heartRate<=77 ? "정상" : (heartRate<74 ? "낮음" : "높음");
        }
        else if(66<heartRate) {
            result = 73<=heartRate&&heartRate<=76 ? "정상" : (heartRate<72 ? "낮음" : "높음");
        } else {
            result = "오류(잘못입력)";
        }

        return result;
    }

}