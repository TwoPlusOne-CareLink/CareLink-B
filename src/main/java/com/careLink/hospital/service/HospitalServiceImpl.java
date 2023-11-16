package com.careLink.hospital.service;

import com.careLink.entity.DoctorInfoEntity;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import com.careLink.entity.DoctorEntity;
import com.careLink.hospital.mapper.HospitalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class HospitalServiceImpl implements HospitalService{

    private final HospitalMapper hospitalMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberEntity member) { //member테이블에 의사회원정보 저장
        try {
            hospitalMapper.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "의사 1차 회원가입 실패");
        }

    }

    @Override//의사등록
    public void signup(DoctorEntity doctor) {
        log.info("의사등록 서비스 들어옴");
        String role = "ROLE_DOCTOR"; //의사 권한
        log.info("입력한 비밀번호 : " + doctor.getPassword());
        String password = doctor.getPassword(); //비밀번호
        password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화
        log.info("시발 제발 --> " + password);
        //member테이블

        MemberEntity member = new MemberEntity(doctor.getMemberId(), password, doctor.getMemberName(),
                                                doctor.getMemberEmail(), doctor.getMemberTel(), doctor.getMemberAddress(), doctor.getMemberAddressDetail(),
                                                role, 1, doctor.getAgree(), doctor.getAge(), doctor.getGender() );

        join(member); //의사회원정보 member 테이블에 저장
        log.info("멤버테이블에는 전송 성공");
        //의사 정보 테이블
        DoctorInfoEntity doctorinfo;

        try {
            if(doctor.getFile() != null && !doctor.getFile().isEmpty()) { //파일이 존재할 떄
                MultipartFile file = doctor.getFile();
                String fileName = file.getOriginalFilename();
                doctorinfo = new DoctorInfoEntity(doctor.getMemberId(), doctor.getDepartmentId(), doctor.getHospitalId(), file.getBytes(), fileName);
            }
            else {
                doctorinfo = new DoctorInfoEntity(doctor.getMemberId(), doctor.getDepartmentId(), doctor.getHospitalId(), null, null);
            }
            
            hospitalMapper.upload(doctorinfo); //의사 정보를 테이블에 저장

        }catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원가입 2차 실패");
        }

    }
}
