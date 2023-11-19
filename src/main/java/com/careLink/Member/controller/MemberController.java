package com.careLink.member.controller;


import com.careLink.ResultDto;
import com.careLink.common.Common;
import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingDetailResultDto;
import com.careLink.member.dto.CounselingResultDto;
import com.careLink.member.dto.GetRequestCounselingDto;
import com.careLink.member.dto.ModifyMemberDto;
import com.careLink.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;
    private final Common common;

    @GetMapping("/requestCounseling")//상담신청페이지(이름,아이디)
    public ResponseEntity<GetRequestCounselingDto> getRequestCounselingDto() {
        String id = common.memberId();

        GetRequestCounselingDto getRequestCounselingDto = memberService.getCounselingMemberById(id);

        return new ResponseEntity<>(getRequestCounselingDto, HttpStatus.OK);
    }

    @PostMapping(value = "/requestCounseling", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //비대면상담신청(회원)
    public ResponseEntity<ResultDto> postRequestCounseling(@RequestPart(value = "file", required = false) MultipartFile attach, CounselingEntity counselingEntity) {
        String id = common.memberId();//아이디 받아오기
        counselingEntity.setMemberId(id);
        int counselingId = memberService.saveCounseling(counselingEntity);

        return new ResponseEntity<ResultDto>(new ResultDto(true,counselingId+"번 상담이 등록되었습니다"), HttpStatus.OK);
    }

    @GetMapping("/counselingList") //나의 상담 목록
    public ResponseEntity<List> list(@RequestParam(defaultValue = "1") int pageNo) {

        String id = common.memberId();

        int totalRows = memberService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 5, totalRows, pageNo);

        List<CounselingResultDto> resultList = memberService.getList(counselingPager, id);

//        Map<String, Object> map = new HashMap<>();
//        map.put("list", list);
//        map.put("pager", counselingPager);
        return new ResponseEntity<List>(resultList, HttpStatus.OK);
    }

    @GetMapping("/counselingDetail/{counselingId}") //상담상세정보
    public ResponseEntity<CounselingDetailResultDto> getCounselingDetail(@PathVariable int counselingId) {
        String id = common.memberId();
        log.info("id " + id);
        log.info("no " + counselingId);
        CounselingDetailResultDto counselingDetailResultDto = memberService.getCounselingDetail(counselingId, id);
        if(!id.equals(counselingDetailResultDto.getPatientId())){
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 회원의 상담이 아님");
        }

        return new ResponseEntity<>(counselingDetailResultDto, HttpStatus.OK);
    }

    @PostMapping("/counselingDetail/counselingLike") //상담의사 좋아요
    public ResponseEntity<ResultDto> clickLike(@RequestParam String doctorId) {
        String id = common.memberId();
        int like = memberService.clickLike(id, doctorId);
        return like == 1 ? new ResponseEntity<>(new ResultDto(true, "좋아요 추가 성공"), HttpStatus.OK)
                : new ResponseEntity<>(new ResultDto(true, "좋아요 삭제 성공"), HttpStatus.OK);
    }

    @GetMapping("/modifyMemberInfo") // 수정페이지 들어가면 띄울 회원 정보
    public ResponseEntity<MemberEntity> getMemberIdName() {
        String id = common.memberId();
        MemberEntity memberInfo = memberService.getMemberInfo(id);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PutMapping("/modifyMemberInfo") // 회원 정보 수정
    public ResponseEntity<ResultDto> modifyMemberInfo(@RequestBody ModifyMemberDto memberDto) {
        String id = common.memberId();
        if (!id.equals(memberDto.getMemberId())) {
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 회원의 정보가 아님");
        } else {

            String modifyId = memberService.updateMember(memberDto); //업데이트된 행의 수(1이 아니라면 뭔가 잘못된것)
            return new ResponseEntity<>(new ResultDto(true, modifyId + "님의 회원정보 수정 성공"), HttpStatus.OK);
        }
    }
}
