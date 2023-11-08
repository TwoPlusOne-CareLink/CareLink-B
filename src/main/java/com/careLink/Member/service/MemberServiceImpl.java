package com.careLink.Member.service;

import com.careLink.Member.domain.LoginResult;
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

import java.util.Optional;


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

            //아이디 중복체크
            String idCheck = memberDto.getId();
            boolean idcheck = fingById(idCheck); //아이디 존재시 예외처리 됨

            if(idcheck) {
                return -1; //아이디 중복
            }

            String password = memberDto.getPassword();
            password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화
            memberDto.passwordEncoder(password); //암호화한 비밀번호로 교체

            memberMapper.save(memberDto);
            return memberDto.getMemberId();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(),"회원가입 실패");
        }
    }

    @Override // 로그인
    public LoginResult signIn(SignInDto signInDto) {

        try {
            MemberDto member = memberMapper.findById(signInDto.getId()).orElse(null); //아이디 일치 여부

            if(member != null) { //아이디는 일치
                //비밀번호 찾기
                String encodePassword = member.getPassword(); //db에 저장된 암호화된 비밀번호

                if(!bCryptPasswordEncoder.matches(signInDto.getPassword(), encodePassword)) { //비밀번호 불일치
                    return new LoginResult(HttpStatus.BAD_REQUEST.value(), false, null, "토큰X");
                }
                else {
//                    String jwtToken = jwtTokenProvider.createToken(member.getId(),member.getMemberId());
                    //로그인 성공 & 토큰 생성, 리턴
//                    return jwtToken;
                    return new LoginResult(HttpStatus.OK.value(), true, member, "토큰");
                }

            }
            else {
                return new LoginResult(HttpStatus.BAD_REQUEST.value(), false, null, "토큰X");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(500, "로그인 실패");
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
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패");
        }


    }

}