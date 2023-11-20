package com.careLink.doctor.service;

import com.careLink.common.Common;
import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorDto;
import com.careLink.doctor.dto.DoctorMyCounselingDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.doctor.mapper.DoctorMapper;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.DoctorInfoEntity;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DoctorServiceImpl implements DoctorService {

    private final DoctorMapper doctorMapper;
    private final Common common;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(MemberEntity member) { //member테이블에 의사회원정보 저장(성민)
        try {
            doctorMapper.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "의사 1차 회원가입 실패");
        }

    }
    @Override
    public String signup(DoctorDto doctor) { //성민

        String role = "ROLE_DOCTOR"; //의사 권한

        String password = doctor.getPassword(); //비밀번호
        password = bCryptPasswordEncoder.encode(password); //비밀번호 암호화

        //member테이블
        MemberEntity member = new MemberEntity(doctor.getDoctorId(), password, doctor.getDoctorName(),
                doctor.getDoctorEmail(), doctor.getDoctorTel(), doctor.getDoctorAddress(), doctor.getDoctorAddressDetail(),
                role, 1, doctor.getAge(), doctor.getGender() );

        join(member); //의사회원정보 member 테이블에 저장
        log.info("멤버테이블에는 전송 성공");

        String doctorId = member.getMemberId(); //의사 고유번호
        //의사 정보 테이블
        DoctorInfoEntity doctorinfo;

        try {
            if(doctor.getFile() != null && !doctor.getFile().isEmpty()) { //파일이 존재할 떄
                MultipartFile file = doctor.getFile();
                String fileName = file.getOriginalFilename();
                doctorinfo = new DoctorInfoEntity(doctor.getDoctorId(), doctor.getDepartmentId(), doctor.getHospitalId(), file.getBytes(), fileName);
            }
            else {
                doctorinfo = new DoctorInfoEntity(doctor.getDoctorId(), doctor.getDepartmentId(), doctor.getHospitalId(), null, null);
            }

            doctorMapper.upload(doctorinfo); //의사 정보를 테이블에 저장
            return doctorId; //회원번호 리턴

        }catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원가입 2차 실패");
        }
    }


    @Override //본인(의사)의 부서와 맞는 상담이 안달린 댓글 개수 가져오기
    public int getNoReplyCount(int departmentId) {
        try{
        return doctorMapper.noReplyCount(departmentId);

        }catch (Exception e){
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 의사의  부서와 맞는 상담이 안달린 댓글 개수 가져오기 실패\"");
        }
    }

    @Override // 본인(의사) 작성한 댓글 개수 가져오기
    public int getMyReplyCount(String doctorId) {
        try {
            return doctorMapper.myReplyCount(doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "본인(의사) 작성한 댓글 개수 가져오기 실패");

        }
    }

    @Override // 로그인한 의사의 부서번호 받아오기
    public int getDepartmentId(String memberId) {
        try {
            return doctorMapper.getDepartmentId(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 의사의 부서번호 못 받아옴");
        }
    }

    @Override  // 로그인한 의사의 진료과목에 맞는 댓글 안달린 상담 목록
    public List<DoctorCounselingListDto> doctorGetList(CounselingPager pager, int departmentId) {
        try {
            List<CounselingEntity> list = doctorMapper.doctorSelectDCounselingByPage(pager, departmentId);
            List<DoctorCounselingListDto> resultList = new ArrayList<>();
            String base64Image;
            for (CounselingEntity counselingEntity : list) {
                // 이미지를 base64로 인코딩
                if (counselingEntity.getCounselingImage() == null) {
                    base64Image = null;
                } else {
                    base64Image = common.convertImageToBase64(counselingEntity.getCounselingImage());

                }
                // base64로 인코딩된 이미지를 새로운 DTO에 설정
                DoctorCounselingListDto resultDto = new DoctorCounselingListDto();
                resultDto.toResult(counselingEntity, base64Image);
                resultList.add(resultDto);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 의사의 진료과목과 같은 상담 중 댓글 안달린 상담 목록 가져오기 실패");
        }
    }

    @Override // 자신이 댓글단 게시물 목록
    public List<DoctorMyCounselingResultDto> doctorGetMyCounseling(CounselingPager pager, String doctorId) {
        try {
            List<DoctorMyCounselingDto> list = doctorMapper.doctorSelectMyCounseling(pager, doctorId);
            List<DoctorMyCounselingResultDto> resultList = new ArrayList<>();
            String base64Image;
            for (DoctorMyCounselingDto doctorMyCounselingDto : list) {
                // 이미지를 base64로 인코딩
                if (doctorMyCounselingDto.getCounselingImage() == null) {
                    base64Image = null;
                } else {
                    base64Image = common.convertImageToBase64(doctorMyCounselingDto.getCounselingImage());

                }

                // base64로 인코딩된 이미지를 새로운 DTO에 설정
                DoctorMyCounselingResultDto resultDto = new DoctorMyCounselingResultDto();
                resultDto.toResult(doctorMyCounselingDto, base64Image);
                resultList.add(resultDto);

            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "내(의사)가 댓글 단 목록 불러오기 실패");
        }
    }

    @Override // 댓글 달기
    public int addReply(int counselingId, String memberId, String commentContent) {
        try {
            doctorMapper.insertReply(counselingId, memberId, commentContent);
            return counselingId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글 등록 실패");
        }
    }
}
