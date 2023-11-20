package com.careLink.doctor.service;

import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.entity.CounselingPager;
import com.careLink.member.dto.CounselingResultDto;

import java.util.List;

//의사
public interface DoctorService {
    public String signup(DoctorDto doctor); // //의사회원가입(성민)
    public int getNoReplyCount(int departmentId); //댓글 안달린 상담목록 개수 가져오기
    public int getMyReplyCount(String doctorId); // 본인(의사)가 댓글 단 상담의 개수 가져오기
    public int getDepartmentId(String memberId); // 로그인된 의사의 부서번호 가져오기
    public List<DoctorCounselingListDto> doctorGetList(CounselingPager pager, int departmentId); // 의사의 진료 과목에 따른 댓글이 안 달린 상담리스트 가져오기
    public List<DoctorMyCounselingResultDto> doctorGetMyCounseling(CounselingPager pager, String doctorId);
    public int addReply(int counselingId,String memberId,String commentContent);
}
