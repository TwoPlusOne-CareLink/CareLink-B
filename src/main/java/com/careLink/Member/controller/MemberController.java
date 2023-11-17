package com.careLink.member.controller;


import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.CounselingDetailDto;
import com.careLink.member.dto.CounselingDetailResultDto;
import com.careLink.member.dto.CounselingResultDto;
import com.careLink.member.dto.GetRequestCounselingDto;
import com.careLink.member.result.*;
import com.careLink.member.service.MemberService;
import com.careLink.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public final MemberService memberService;

    @GetMapping("/requestCounseling")//상담신청페이지(이름,아이디)
    public ResponseEntity<GetRequestCounselingDto> getRequestCounselingDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        GetRequestCounselingDto getRequestCounselingDto = memberService.getCounselingMemberById(id);

        return new ResponseEntity<>(getRequestCounselingDto, HttpStatus.OK);
    }

    @PostMapping(value = "/requestCounseling", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //비대면상담신청(회원)
    public ResponseEntity<Integer> postRequestCounseling
            (@RequestPart(value = "file", required = false) MultipartFile attach, CounselingEntity counselingDto) {
//      아이디 받아옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        counselingDto.setMemberId(id);
        int counselingId = memberService.saveCounseling(counselingDto);

        return new ResponseEntity<>(counselingId, HttpStatus.OK);
    }

    @GetMapping("/counselingList") //나의 상담 목록
    public ResponseEntity<Map> list(@RequestParam(defaultValue = "1") int pageNo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        int totalRows = memberService.getCount();
        CounselingPager counselingPager = new CounselingPager(8, 5, totalRows, pageNo);

        List<CounselingResultDto> list = memberService.getList(counselingPager, id);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pager", counselingPager);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @GetMapping("/counselingDetail/{counselingId}") //상담상세정보
    public ResponseEntity<CounselingDetailResultDto> getCounSelingDetail(@PathVariable int counselingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        log.info("id " + id);
        log.info("no " + counselingId);
        CounselingDetailResultDto counselingDetailResultDto = memberService.getCounselingDetail(counselingId);
        if (!id.equals(counselingDetailResultDto.getPatientId())) {
            log.info("받아온정보"+counselingDetailResultDto.getPatientId());
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "자신의 정보 아님");
        }
        return new ResponseEntity<>(counselingDetailResultDto, HttpStatus.OK);
    }

    @PostMapping("/counselingDetail/counselingLike") //상담의사 좋아요
    public ResponseEntity<Integer> clickLike(@RequestParam String doctorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        int like = memberService.clickLike(memberId, doctorId);
        return new ResponseEntity<>(like, HttpStatus.OK);
    }
}
