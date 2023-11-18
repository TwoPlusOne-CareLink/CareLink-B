package com.careLink.doctor.mapper;

import com.careLink.doctor.dto.DoctorMyCounselingDto;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//의사
@Mapper
public interface DoctorMapper {
    public int count();

    public int getDepartmentId(@Param("memberId") String memberId); // 진료과목 번호 받아오기

    public List<CounselingEntity> doctorSelectDCounselingByPage
            (@Param("pager") CounselingPager pager,
             @Param("doctorDepartmentId") int doctorDepartmentId); //자신의 진료과목에 맞는 상담이 필요한 리스트 받아오기

    public List<DoctorMyCounselingDto> doctorSelectMyCounseling
            (@Param("pager") CounselingPager pager,
             @Param("doctorId") String doctorId); //자신이 상담한 목록들 받아오기

    public int insertReply(@Param("counselingId") int counselingId,
                           @Param("memberId") String memberId,
                           @Param("commentContent") String commentContent);


}