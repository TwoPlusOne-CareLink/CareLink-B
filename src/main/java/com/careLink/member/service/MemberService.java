package com.careLink.member.service;

import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.member.dto.GetRequestCounselingDto;

import java.util.List;

public interface MemberService {

    public GetRequestCounselingDto getCounselingMemberById(String id); // 로그인된아이디로 상담할때 필요한 아이디,이름 받기
    public int saveCounseling(CounselingEntity counselingDto);  // 상담 저장
    public int getCount(); //상담개수가져오기
    public List<CounselingEntity> getList(CounselingPager pager, String id); // 내 상담 내역 가져오기
}
