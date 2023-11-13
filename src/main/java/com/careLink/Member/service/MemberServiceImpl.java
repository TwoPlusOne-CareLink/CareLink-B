package com.careLink.Member.service;

import com.careLink.Member.ReturnData.LoginResult;
import com.careLink.Member.domain.MemberDto;
import com.careLink.Member.domain.SignInDto;
import com.careLink.Member.mapper.MemberMapper;
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
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberMapper memberMapper;

    @Override //회원가입
    public int signUp(MemberDto memberDto) {
        try {
            log.info("아이디 중복체크");
            //아이디 중복체크
            String idCheck = memberDto.getId();
            boolean idcheck = fingById(idCheck); //아이디 존재시 예외처리 됨

            if(idcheck) {
                log.info("아이디 중복임");
                return -1; //아이디 중복
            }
            log.info("아이디 중복아님");
            String password = memberDto.getPassword();
            password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화
            memberDto.passwordEncoder(password); //암호화한 비밀번호로 교체
            log.info("db에 회원정보 저장");
            memberMapper.save(memberDto);
            log.info("회원정보 저장 완료");
            return memberDto.getMemberId();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(),"회원가입 실패");
        }
    }
    

    //아이디 중복 체크
    public boolean fingById(String id) {
        try {
            log.info("아이디 중복 체크 : " + id);
            MemberDto member = memberMapper.findById(id).orElse(null);

            if(member != null) {
                log.info("아이디가 중복이다.");
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패"); // 회원가입 실패
        }


    }

}