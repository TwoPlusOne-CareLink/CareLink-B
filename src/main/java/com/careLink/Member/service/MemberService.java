package com.careLink.member.service;

import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.MemberEntity;
import com.careLink.member.dto.*;

import java.util.List;

public interface MemberService {

    public GetRequestCounselingDto getCounselingMemberById(String id); // 로그인된아이디로 상담할때 필요한 아이디,이름 받기
    public int saveCounseling(CounselingEntity counselingDto);  // 상담 저장
    public int getCount(); //상담개수가져오기
    public List<CounselingResultDto> getList(CounselingPager pager, String id); // 내 상담 내역 가져오기
    public CounselingDetailResultDto getCounselingDetail(int no,String id); // 상세 정보
    public int clickLike(String memberId,String doctorId); // 좋아요
    public MemberEntity getMemberInfo(String memberId); // 회원 정보 가져오기
    public String updateMember(ModifyMemberDto modifyMemberDto); //회원 정보 수정
}
