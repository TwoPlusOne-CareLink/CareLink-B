package com.careLink.doctor.dto;

import com.careLink.entity.CounselingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCounselingListDto {

    private int counselingId;
    private String counselingTitle;
    private String memberId;
    private int departmentId;
    private String counselingContent;
    private Date counselingDate;
    private String counselingImage;
    private String counselingImageName;

    public void toResult(CounselingEntity counselingEntity, String base64Img) {
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
