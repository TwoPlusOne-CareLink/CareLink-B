package com.careLink.member.service;

import com.careLink.common.Common;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.*;
import com.careLink.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final Common common;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override //상담신청 페이지(회원정보넣기)
    public GetRequestCounselingDto getCounselingMemberById(String id) {
        GetRequestCounselingDto getRequestCounselingDto = memberMapper.counselingMemberById(id)
                .orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "상담신청에 넣을 회원정보 가져오기 실패"));
        return getRequestCounselingDto;
    }

    @Override //상담신청 저장
    public int saveCounseling(CounselingEntity counselingEntity) {
        try {
            if (counselingEntity.getAttach() != null && !counselingEntity.getAttach().isEmpty()) {
                MultipartFile mf = counselingEntity.getAttach();
                counselingEntity.setCounselingImageName(mf.getOriginalFilename());

                counselingEntity.setCounselingImage(mf.getBytes());


            } else {
                counselingEntity.setCounselingImage(null);
                counselingEntity.setCounselingImageName(null);

                log.info("이미지없음");
            }
            memberMapper.saveCounseling(counselingEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "상담 신청 실패");
        }
        log.info("상담정보 저장 완료");
        return counselingEntity.getCounselingId();
    }

    @Override //게시물 전체갯수(페이징)
    public int getCount() {
        try {

            return memberMapper.count();
        } catch (Exception e) {
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "게시물 개수 불러오기 실패");
        }
    }

    @Override//내 상담 내역
    public List<CounselingResultDto> getList(CounselingPager pager, String id) {
        try {
            List<CounselingEntity> list = memberMapper.selectCounselingByPage(pager, id);
            List<CounselingResultDto> resultList = new ArrayList<>();
            String base64Image;
            for (CounselingEntity counselingEntity : list) {
                // 이미지를 base64로 인코딩
                if (counselingEntity.getCounselingImage() == null) {
                    base64Image = null;
                } else {
                    base64Image = common.convertImageToBase64(counselingEntity.getCounselingImage());
                }
                // base64로 인코딩된 이미지를 새로운 DTO에 설정
                CounselingResultDto resultDto = new CounselingResultDto();
                resultDto.toResult(counselingEntity, base64Image);
                resultList.add(resultDto);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "내 상담목록 불러오기 실패");
        }
    }

    @Override //상담상세정보
    public CounselingDetailResultDto getCounselingDetail(int counselingId, String id) {  //상세정보받기
        try {
            int replyCount = checkReply(counselingId);
            CounselingDetailResultDto resultDto = new CounselingDetailResultDto();
            String counselingBase64Image, doctorBase64Image;
            if (replyCount > 0) { //댓글이 있을 때

                CounselingDetailDto counselingDetailDto = memberMapper.yesReplyCounselingDetail(counselingId)
                        .orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글이 있을 때의 데이터 불러오기 실패"));
                if (counselingDetailDto.getCounselingImage() == null) { //댓글있고, 상담사진없으면
                    log.info("댓글있는데 회원이미지없");
                    doctorBase64Image = common.convertImageToBase64(counselingDetailDto.getDoctorImage());

                    resultDto.YesImageToResult(counselingDetailDto, null, doctorBase64Image);

                } else { //댓글있고 사진있을때
                    counselingBase64Image = common.convertImageToBase64(counselingDetailDto.getCounselingImage());
                    doctorBase64Image = common.convertImageToBase64(counselingDetailDto.getDoctorImage());
                    resultDto.YesImageToResult(counselingDetailDto, counselingBase64Image, doctorBase64Image);
                }

            } else { // 댓글이 없을 때 (의사사진할필요 x)
                CounselingDetailDto counselingDetailDto = memberMapper.noReplyCounselingDetail(counselingId)
                        .orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글이 없을 때의 데이터 불러오기 실패"));
                if (counselingDetailDto.getCounselingImage() == null) { //댓글없고 상담사진없으면
                    log.info("댓글없 회원이미지없");

                    resultDto.YesImageToResult(counselingDetailDto, null, null);

                } else { //댓글없고 사진있을때
                    counselingBase64Image = common.convertImageToBase64(counselingDetailDto.getCounselingImage());
                    resultDto.YesImageToResult(counselingDetailDto, counselingBase64Image, null);
                }

            }

            return resultDto;

        } catch (ErrorException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "상세 정보 불러오기 실패");
        }
    }

    // 댓글여부확인
    public int checkReply(int counselingId) {
        try {
            return memberMapper.checkReply(counselingId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "댓글 존재 여부 확인 실패");
        }
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

    @Override //회원 정보 가져오기
    public MemberEntity getMemberInfo(String memberId) {

        try {
            MemberEntity memberEntity = memberMapper.selectMemberInfo(memberId)
                    .orElseThrow(() -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "해당 회원 정보 없음"));
            return memberEntity;
        }catch (ErrorException e){
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원 정보 가져오기 실패");
        }
    }

    @Override // 회원 정보 수정
    public String updateMember(ModifyMemberDto modifyMemberDto) {
        try {
            String password = modifyMemberDto.getPassword();
            password = bCryptPasswordEncoder.encode(password);
            modifyMemberDto.passwordEncode(password);
            memberMapper.modifyMember(modifyMemberDto);
            String id = modifyMemberDto.getMemberId();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원 정보 수정 실패");
        }
    }
}
