package com.careLink.auth.service;
import com.careLink.auth.mapper.AuthMapper;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;

    //회원가입
    @Override
    public String signUp(MemberEntity memberEntity) {
        try {
            String idCheck = memberEntity.getMemberId(); //아이디 중복체크
            boolean idcheck = findById(idCheck); //아이디 존재시 예외처리 됨

            if (idcheck) {
                throw new ErrorException(HttpStatus.CONFLICT.value(), "아이디가 중복임");
            }

            String password = memberEntity.getPassword();
            password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화
            memberEntity.passwordEncoder(password); //암호화한 비밀번호로 교체

            authMapper.join(memberEntity); //mepper를 사용해서 DB에 member 테이블에 memberEntity(회원정보) 저장
            return memberEntity.getMemberId(); //회원고유번호 리턴

        } catch (ErrorException e) {
            throw e;
        }catch (Exception e) { //회원가입 실패시 예외처리
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원가입 실패");
        }
    }

    //아이디 중복 체크
    public boolean findById(String id) {
        try {
            log.info("아이디 중복 체크");
            MemberEntity member = authMapper.findById(id).orElse(null);
            return member != null ? true : false; //true:중복, false:중복x
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패"); // 회원가입 실패(400)
        }
    }
    
}
