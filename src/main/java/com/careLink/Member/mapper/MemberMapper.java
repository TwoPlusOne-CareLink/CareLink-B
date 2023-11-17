package com.careLink.member.mapper;

import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.member.dto.CounselingDetailDto;
import com.careLink.member.dto.GetRequestCounselingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    public Optional<GetRequestCounselingDto> counselingMemberById(String id); //상담신청에 넣을 글쓴이의 아이디,이름 받아오기
    public void saveCounseling(CounselingEntity counselingEntity); //상담저장
    public int count(); //상담 개수 가져오기(페이징에 필요)
    public List<CounselingEntity> selectCounselingByPage(@Param("pager") CounselingPager pager, @Param("memberId") String memberId); //상담 목록
    public Optional<CounselingDetailDto> noReplyCounselingDetail(@Param("counselingId") int counselingId); //상담상세정보 받아오기
    public Optional<CounselingDetailDto> yesReplyCounselingDetail(@Param("counselingId") int counselingId); //상담상세정보 받아오기
    public int checkLike(@Param("memberId")String memberId,@Param("doctorId")String doctorId); //좋아요 확인
    public int insertLike(@Param("memberId") String memberId, @Param("doctorId") String doctorId); //좋아요 추가
    public int deleteLike(@Param("memberId") String memberId, @Param("doctorId") String doctorId); //좋아요 삭제
    public int checkReply(@Param("counselingId") int counselingId);

}
