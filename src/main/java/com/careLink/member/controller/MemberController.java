package com.careLink.member.controller;


import com.careLink.entity.CounselingEntity;
import com.careLink.entity.CounselingPager;
import com.careLink.member.dto.GetRequestCounselingDto;
import com.careLink.member.result.CounselingListResult;
import com.careLink.member.result.GetRequestCounselingResult;
import com.careLink.member.result.PostResult;
import com.careLink.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/member")
public class MemberController {

    public final MemberService memberService;

    @GetMapping("/requestCounseling")//상담신청페이지(이름,아이디)
    public GetRequestCounselingResult getRequestCounselingDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        GetRequestCounselingDto Dto = memberService.getCounselingMemberById(id);

        return new GetRequestCounselingResult(HttpStatus.OK.value(), true, Dto);
    }
    @PostMapping(value="/requestCounseling", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //비대면상담신청(회원)
    public PostResult postRequestCounseling(@RequestPart MultipartFile attach,
                                            CounselingEntity counselingDto) {
//      아이디 받아옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        counselingDto.setMemberId(id);
        memberService.saveCounseling(counselingDto);
        return new PostResult(HttpStatus.OK.value(), true);
    }

    @GetMapping("/counselingList") //나의 상담 목록
    public CounselingListResult list(@RequestParam(defaultValue = "1") int pageNo ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        int totalRows = memberService.getCount();
        CounselingPager counselingPager = new CounselingPager(8,5,totalRows,pageNo);

        List<CounselingEntity> list = memberService.getList(counselingPager,id);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pager", counselingPager);
        return new CounselingListResult(HttpStatus.OK.value(),true,map);
    }

}
