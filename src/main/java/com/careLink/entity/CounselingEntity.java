package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingEntity {
    private int counselingId;
    private String counselingTitle;
    private String memberId;
    private int departmentId;
    private String counselingContent;
    private Date counselingDate;
    private MultipartFile attach;
    private byte[] counselingImage;
    private String counselingImageName;

}