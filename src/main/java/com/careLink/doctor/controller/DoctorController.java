package com.careLink.doctor.controller;

import com.careLink.common.Common;
import com.careLink.doctor.dto.DoctorCounselingListDto;
import com.careLink.doctor.dto.DoctorMyCounselingResultDto;
import com.careLink.doctor.dto.ReplyDataDto;
import com.careLink.doctor.service.DoctorService;
import com.careLink.entity.CounselingPager;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingDetailResultDto;
import com.careLink.member.dto.CounselingResultDto;
import com.careLink.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final MemberService memberService;
    private final DoctorService doctorService;
    private final Common common;

    @GetMapping("/counselingList") //의사의 부서에 따른 답글이 안달린 상담 목록
    public ResponseEntity<List> list(@RequestParam(defaultValue = "1") int pageNo) {
        String id = common.memberId();
        int doctorDepartmentId = doctorService.getDepartmentId(id);
        log.info("==================" + id + "===================" + doctorDepartmentId);

        int totalRows = doctorService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 5, totalRows, pageNo);

        List<DoctorCounselingListDto> list = doctorService.doctorGetList(counselingPager, doctorDepartmentId);

//        Map<String, Object> map = new HashMap<>();
//        map.put("list", list);
//        map.put("pager", counselingPager);

        return new ResponseEntity<List>(list, HttpStatus.OK);
    }

    @GetMapping("/myCounselingResult") // 자신이 댓글단 상담 목록 받아 오기
    public ResponseEntity<List> getMyResult(@RequestParam(defaultValue = "1") int pageNo) {
        String id = common.memberId();

        int totalRows = doctorService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 8, totalRows, pageNo);

        List<DoctorMyCounselingResultDto> list = doctorService.doctorGetMyCounseling(counselingPager, id);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/createReply") // 댓글 달기
    public ResponseEntity<Integer> createReply(@RequestBody ReplyDataDto replyDataDto) {
        String id = common.memberId();

        int success = doctorService.addReply(replyDataDto.getCounselingId(), id, replyDataDto.getCommentContent());

        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/counselingDetail/{counselingId}") //상담상세정보조회
    public ResponseEntity<CounselingDetailResultDto> getCounSelingDetail(@PathVariable int counselingId) {
        String id = common.memberId();

        CounselingDetailResultDto counselingDetailResultDto = memberService.getCounselingDetail(counselingId,id);
        if (!id.equals(counselingDetailResultDto.getDoctorId())) {
            log.info("받아온정보"+counselingDetailResultDto.getDoctorId());
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "자신의 정보 아님(의사)");
        }

        return new ResponseEntity<>(counselingDetailResultDto, HttpStatus.OK);
    }


}

