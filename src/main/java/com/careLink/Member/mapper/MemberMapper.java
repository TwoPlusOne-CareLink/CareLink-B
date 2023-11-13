package com.careLink.Member.mapper;

import com.careLink.Member.dto.GetRequestCounselingDto;
import com.careLink.Member.vo.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

//회원가입&로그인, 마이페이지(회원정보수정), 비대면상담
@Mapper
public interface MemberMapper {
    public void save(MemberDto memberDto); //db에 회원정보저장 - 회원가입
    public Optional<MemberDto> findById(String id); //아이디로 회원정보 찾기
    public Optional<GetRequestCounselingDto> counselingMemberById(String id);
}