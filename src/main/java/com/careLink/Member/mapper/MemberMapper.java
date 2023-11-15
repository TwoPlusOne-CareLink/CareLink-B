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
    public Optional<GetRequestCounselingDto> counselingMemberById(String id); //
    public void saveCounseling(CounselingEntity counselingEntity);
    public int count();
    public List<CounselingEntity> selectCounselingByPage(@Param("pager") CounselingPager pager, @Param("memberId") String memberId);
    public Optional<CounselingDetailDto> selectCounselingDetail(@Param("counselingId") int counselingId);
}
