package com.careLink.doctor.mapper;

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
    public List<CounselingEntity> doctorSelectDCounselingByPage(@Param("pager") CounselingPager pager, @Param("doctorDepartmentId") int doctorDepartmentId); //상담 목록



}