package com.careLink.member.service;

import com.careLink.entity.HospitalEntity;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.HospitalInfoDto;
import com.careLink.member.dto.LatLngDto;
import com.careLink.member.mapper.CareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//병원찾기 && 헬스 케어(일일체크 진단)
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CareServiceImpl implements CareService {
    
    private final CareMapper careMapper;
    
    @Override //병원 전체 목록
    public List<HospitalInfoDto> hospitalInfo() {
        
        try {
            log.info("병원 목록 서비스 들어옴");
            List<HospitalEntity> list = careMapper.hospitalSelect(); //전체 병원 목록 불러오기
            log.info("병원 개수 : " + list.size());
            List<HospitalInfoDto> hInfo = new ArrayList<>(); //리턴해줄 타입

            for(HospitalEntity he : list) {
                LatLngDto latLngDto = new LatLngDto(he.getLat(), he.getLng());
                int hId = he.getHospitalId();
                String hName = he.getHospitalName();
                String ad = he.getAddress();
                String tel = he.getTel();


                HospitalInfoDto dto = HospitalInfoDto.builder()
                                        .hospitalId(hId)
                                        .hospitalName(hName)
                                        .address(ad)
                                        .latlng(latLngDto)
                                        .tel(tel)
                                        .build();

                hInfo.add(dto);
            }

            return hInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 정보 찾기 실패");
        }
        
    }
}
