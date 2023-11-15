package com.careLink.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClickLikeDto {
    private String memberId;
    private String doctorId;
}
