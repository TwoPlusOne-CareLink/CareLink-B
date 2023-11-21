package com.careLink.member.mapper;

import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.MemberEntity;
import com.careLink.member.dto.CounselingDetailDto;
import com.careLink.member.dto.GetRequestCounselingDto;
import com.careLink.member.dto.ModifyMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    //상담신청에 넣을 글쓴이의 아이디,이름 받아오기
    public Optional<GetRequestCounselingDto> counselingMemberById(String id);

    //상담저장
    public void saveCounseling(CounselingEntity counselingEntity);

    //상담 개수 가져오기(페이징에 필요)
    public int count();

    //상담 목록 받아오기
    public List<CounselingEntity> selectCounseling(@Param("memberId") String memberId);

    //댓글이 없는 상담 상세 정보 받아오기
    public Optional<CounselingDetailDto> noReplyCounselingDetail(@Param("counselingId") int counselingId);

    //댓글이 있는 상담 상세 정보 받아오기
    public Optional<CounselingDetailDto> yesReplyCounselingDetail(@Param("counselingId") int counselingId);

    //좋아요 확인
    public int checkLike(@Param("memberId") String memberId, @Param("doctorId") String doctorId);

    //좋아요 추가
    public int insertLike(@Param("memberId") String memberId, @Param("doctorId") String doctorId);

    //좋아요 삭제
    public int deleteLike(@Param("memberId") String memberId, @Param("doctorId") String doctorId);

    // 상담에 댓글 등록여부 확인
    public int checkReply(@Param("counselingId") int counselingId);

    //회원정보 가져옴
    public Optional<MemberEntity> selectMemberInfo(@Param("memberId") String memberId);

    //회원 수정
    public void modifyMember(ModifyMemberDto modifyMemberDto) ;

}