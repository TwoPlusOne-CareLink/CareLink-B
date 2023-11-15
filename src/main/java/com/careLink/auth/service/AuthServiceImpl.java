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
    public int signUp(MemberEntity memberEntity) {
        try {
            log.info("회원가입 서비스 들어옴");
            String idCheck = memberEntity.getMemberId(); //아이디 중복체크
            log.info("아이디 : " + idCheck);
            boolean idcheck = fingById(idCheck); //아이디 존재시 예외처리 됨

            if (idcheck) {
                log.info("아이디 중복");
                return -1; //아이디 중복
            }

            log.info("아이디 중복아님");
            String password = memberEntity.getPassword();
            password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화
            memberEntity.passwordEncoder(password); //암호화한 비밀번호로 교체

            authMapper.join(memberEntity);
            return memberEntity.getMemberNo();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원가입 실패");
        }
    }

    //아이디 중복 체크
    public boolean fingById(String id) {
        try {
            MemberEntity member = authMapper.findById(id).orElse(null);
            return member != null ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패"); // 회원가입 실패(400)
        }
    }
    
}
