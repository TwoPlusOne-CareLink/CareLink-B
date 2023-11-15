package com.careLink.hospital.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorJoinResult {
    private int ErrorCode;
    private boolean success;
    private int doctorId;
}
