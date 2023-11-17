package com.careLink.member.dto;

import com.careLink.entity.CounselingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounselingResultDto {

    private int counselingId;
    private String counselingTitle;
    private String memberId;
    private int departmentId;
    private String counselingContent;
    private Date counselingDate;
    private String counselingImage;
    private String counselingImageName;

    public void ToResult(CounselingEntity counselingEntity, String base64Img) {
          counselingId = counselingEntity.getCounselingId();
          counselingTitle = counselingEntity.getCounselingTitle();
          memberId = counselingEntity.getMemberId();
          departmentId = counselingEntity.getDepartmentId();
          counselingContent = counselingEntity.getCounselingContent();
          counselingDate = counselingEntity.getCounselingDate();
          counselingImage = base64Img;
          counselingImageName = counselingEntity.getCounselingImageName();
    }


}

