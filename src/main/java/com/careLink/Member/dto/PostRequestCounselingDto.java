package com.careLink.Member.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PostRequestCounselingDto {
    private int COUNSELING_ID;
    private String COUNSELING_TITLE;
    private int MEMBER_ID;
    private int DEPARTMENT_ID;
    private String COUNSELING_CONTENT;
    private MultipartFile COUNSELING_IMAGE;
    private Date COUNSELING_DATE;
}
