package com.careLink.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDataDto {
    private int counselingId;
    private String commentContent;

}
