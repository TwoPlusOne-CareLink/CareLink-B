package com.careLink.auth.mapper;

import com.careLink.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface AuthMapper {
    public void join(MemberEntity memberEntity); //db에 회원정보저장 - 회원가입
    public Optional<MemberEntity> findById(String memberId); //아이디로 회원정보 찾기
}
