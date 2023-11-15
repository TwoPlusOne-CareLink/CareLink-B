package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeEntity {
    private int likeId;
    private String memberId;
    private String doctorId;
}
