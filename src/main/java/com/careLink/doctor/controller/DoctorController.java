package com.careLink.doctor.controller;

import com.careLink.ResultDto;
import com.careLink.common.Common;
import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.doctor.dto.ReplyDataDto;
import com.careLink.doctor.service.DoctorService;
import com.careLink.member.dto.CounselingDetailResultDto;
import com.careLink.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final MemberService memberService;
    private final DoctorService doctorService;
    private final Common common;

    @GetMapping("/counselingList") //의사의 부서에 따른 답글이 안달린 상담 목록
    public ResponseEntity<List<DoctorCounselingListDto>> list() {
        String id = common.memberId();
        int doctorDepartmentId = doctorService.getDepartmentId(id);

        List<DoctorCounselingListDto> list = doctorService.doctorGetList(doctorDepartmentId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/myCounselingResult") // 자신이 댓글단 상담 목록 받아 오기
    public ResponseEntity<List> getMyResult() {
        String id = common.memberId();

        List<DoctorMyCounselingResultDto> list = doctorService.doctorGetMyCounseling(id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/createReply") // 댓글 달기
    public ResponseEntity<ResultDto> createReply(@RequestBody ReplyDataDto replyDataDto) {
        String id = common.memberId();

        int counselingId = doctorService.addReply(replyDataDto.getCounselingId(), id, replyDataDto.getCommentContent());

        return new ResponseEntity<>(new ResultDto(true, counselingId + "번 상담에 댓글 등록 성공"), HttpStatus.OK);
    }

    @GetMapping("/counselingDetail/{counselingId}") //상담상세정보조회
    public ResponseEntity<CounselingDetailResultDto> getCounselingDetail(@PathVariable int counselingId) {
        String id = common.memberId();

        CounselingDetailResultDto counselingDetailResultDto = memberService.getCounselingDetail(counselingId, id);

        return new ResponseEntity<>(counselingDetailResultDto, HttpStatus.OK);
    }


}