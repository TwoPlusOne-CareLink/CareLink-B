package com.careLink.member.service;

import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingDetailDto;
import com.careLink.member.dto.GetRequestCounselingDto;
import com.careLink.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override //상담신청 페이지(회원정보넣기)
    public GetRequestCounselingDto getCounselingMemberById(String id) {
        GetRequestCounselingDto getRequestCounselingDto = memberMapper.counselingMemberById(id).orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "정보 못 받아옴"));
        return getRequestCounselingDto;
    }

    @Override //상담신청
    public int saveCounseling(CounselingEntity counselingEntity) {
        if (counselingEntity.getAttach() != null && !counselingEntity.getAttach().isEmpty()) {
            MultipartFile mf = counselingEntity.getAttach();
            counselingEntity.setCounselingImageName(mf.getOriginalFilename());
            try {
                counselingEntity.setCounselingImage(mf.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "이미지 저장 실패");
            }
            memberMapper.saveCounseling(counselingEntity);

        }

        log.info("상담정보 저장 완료");
        return counselingEntity.getCounselingId();
    }

    @Override //게시물 전체갯수(페이징)
    public int getCount() {
        return memberMapper.count();
    }

    @Override//내 상담 내역
    public List<CounselingEntity> getList(CounselingPager pager, String id) {
        return memberMapper.selectCounselingByPage(pager, id);
    }

    @Override
    public CounselingDetailDto getCounselingDetail(int no) {
        CounselingDetailDto counselingDetailDto = memberMapper.selectCounselingDetail(no).orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "정보 못 받아옴"));
        return counselingDetailDto;
    }

    @Override //좋아요 클릭했을 때 likeCheck 메소드 실행값에 따라 추가 or 삭제
    public int clickLike(String memberId, String doctorId) {

        int likeCount = likeCheck(memberId, doctorId);
        int result = 0;

        try {
            //좋아요 삭제 또는 추가 실행
            if (likeCount == 1) {
                memberMapper.deleteLike(memberId, doctorId);
                result = 0;
            } else {
                memberMapper.insertLike(memberId, doctorId);
                result = 1;
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "좋아요 실행 실패");
        }

    }
//  좋아요 체크
    public int likeCheck(String memberId, String doctorId) {
        try {
            return memberMapper.checkLike(memberId, doctorId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "좋아요 여부 체크 실패");
        }
    }

}
