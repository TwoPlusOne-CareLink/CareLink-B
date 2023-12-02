package com.careLink.doctor.mapper;

import com.careLink.doctor.dto.DoctorMyCounselingDto;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.DoctorInfoEntity;
import com.careLink.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//의사
@Mapper
public interface DoctorMapper {
    public void save(MemberEntity member); //회원 테이블에 의사 정보 저장

    public void upload(DoctorInfoEntity doctorInfo); //의사정보 테이블에 저장

    public int noReplyCount(@Param("departmentId") int departmentId); ////본인(의사)의 부서와 맞는 상담이 안달린 댓글 개수 가져오기

    public int myReplyCount(@Param("doctorId") String doctorId); //본인(의사) 작성한 댓글 개수 가져오기

    public int getDepartmentId(@Param("memberId") String memberId); // 진료과목 번호 받아오기

    public List<CounselingEntity> doctorSelectCounseling
            (@Param("doctorDepartmentId") int doctorDepartmentId); //자신의 진료과목에 맞는 상담이 필요한 리스트 받아오기
//            (@Param("pager") CounselingPager pager,@Param("doctorDepartmentId") int doctorDepartmentId); // 위에꺼 페이징

    public List<DoctorMyCounselingDto> doctorSelectMyCounseling
            (@Param("doctorId") String doctorId); //자신이 상담한 목록들 받아오기

    public int insertReply(@Param("counselingId") int counselingId,
                           @Param("memberId") String memberId,
                           @Param("commentContent") String commentContent);
}