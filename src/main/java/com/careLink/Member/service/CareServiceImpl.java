package com.careLink.Member.service;

import com.careLink.Member.domain.HospitalDto;
import com.careLink.Member.mapper.CareMapper;
import com.careLink.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//병원찾기 && 헬스 케어(일일체크 진단)
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CareServiceImpl implements CareService {
    
    private final CareMapper careMapper;
    
    @Override //병원 전체 목록
    public List<HospitalDto> hospitalInfo() {
        
        try {
            log.info("여기는 들어옴");
            List<HospitalDto> list = careMapper.hospitalInfo(); //전체 병원 목록 불러오기
            log.info("크기 : " + list.size());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 정보 찾기 실패");
        }
        
    }
}
