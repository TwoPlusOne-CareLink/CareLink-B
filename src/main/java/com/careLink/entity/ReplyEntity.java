package com.careLink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor@NoArgsConstructor
public class ReplyEntity {
    private int replyId;
    private int counselingId;
    private String memberId;
    private String commentContent;
    private Date commnetDate;
}
