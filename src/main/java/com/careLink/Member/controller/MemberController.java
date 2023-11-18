package com.careLink.member.controller;


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
    public ResponseEntity<Integer> postRequestCounseling(@RequestPart(value = "file", required = false) MultipartFile attach, CounselingEntity counselingEntity) {
//      아이디 받아옴
        String id = common.memberId();
        counselingEntity.setMemberId(id);
        int counselingId = memberService.saveCounseling(counselingEntity);

        return new ResponseEntity<>(counselingId, HttpStatus.OK);
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
    public ResponseEntity<CounselingDetailResultDto> getCounSelingDetail(@PathVariable int counselingId) {
        String id = common.memberId();
        log.info("id " + id);
        log.info("no " + counselingId);
        CounselingDetailResultDto counselingDetailResultDto = memberService.getCounselingDetail(counselingId, id);

        return new ResponseEntity<>(counselingDetailResultDto, HttpStatus.OK);
    }

    @PostMapping("/counselingDetail/counselingLike") //상담의사 좋아요
    public ResponseEntity<Integer> clickLike(@RequestParam String doctorId) {
        String id = common.memberId();
        int like = memberService.clickLike(id, doctorId);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }

    @GetMapping("/modifyMemberInfo") // 수정페이지 들어가면 띄울 회원 정보
    public ResponseEntity<MemberEntity> getMemberIdName() {
        String id = common.memberId();
        MemberEntity memberInfo = memberService.getMemberInfo(id);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    @PutMapping("/modifyMemberInfo") // 회원 정보 수정
    public ResponseEntity<Integer> modifyMemberInfo(@RequestBody ModifyMemberDto memberDto) {
        String id = common.memberId();
        if (!id.equals(memberDto.getMemberId())) {
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인한 회원의 정보가 아님");
        } else {

            int result = memberService.updateMember(memberDto); //업데이트된 행의 수(1이 아니라면 뭔가 잘못된것)
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
