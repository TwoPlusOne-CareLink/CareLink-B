package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingEntity { //상담 테이블
    private int counselingId;
    private String counselingTitle;
    private String memberId;
    private int departmentId;
    private String counselingContent;
    private Date counselingDate;
//    private MultipartFile counselingAttach;
    private byte[] counselingImage;
    private String counselingImageName;

}