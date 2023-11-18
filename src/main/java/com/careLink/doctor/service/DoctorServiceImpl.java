package com.careLink.doctor.service;

import com.careLink.common.Common;
import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorMyCounselingDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.doctor.mapper.DoctorMapper;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DoctorServiceImpl implements DoctorService {

    private final DoctorMapper doctorMapper;
    private final Common common;

    @Override // 댓글 안달린 상담목록 개수 가져오기
    public int getCount() {
        try {
            return doctorMapper.count();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글 안달린 상담목록 개수 못 받아옴");

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

    @Override  // 로그인한 의사의 진료과목에 같은 댓글 안달린 상담 목록
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
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "의사의 상담 목록 불러오기 실패");
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
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "목록 불러오기 실패");
        }
    }

    @Override // 댓글 달기
    public int addReply(int counselingId, String memberId, String commentContent) {
        try {
            int replyId = doctorMapper.insertReply(counselingId, memberId, commentContent);
            return replyId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글 등록 실패");
        }
    }
}
