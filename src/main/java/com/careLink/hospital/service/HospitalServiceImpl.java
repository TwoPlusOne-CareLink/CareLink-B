package com.careLink.hospital.service;

import com.careLink.common.Common;
import com.careLink.exception.ErrorException;
import com.careLink.hospital.dto.*;
import com.careLink.hospital.mapper.HospitalMapper;
import com.careLink.member.dto.ReservationDefaultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class HospitalServiceImpl implements HospitalService {

    private final HospitalMapper hospitalMapper;
    private final Common common;

    @Override
    public int hospitalId(String hAdminId) { //소속 병원 아이디값 가져오기
        try {
            return hospitalMapper.hospitalId(hAdminId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "소속병원이 없습니다.에러");
        }
    }

    @Override
    public List<DoctorListDto> doctorList(int hospitalId) {//의사 목록
        try {
            List<DoctorListDto> dList = hospitalMapper.doctorList(hospitalId);
            return dList;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new ErrorException(HttpStatus.BAD_REQUEST.value(), "의사목록 불러오기 실패");
        }
    }

    @Override
    public List<ReservationInfoDto> reservationList(int hospitalId) { //전체 예약 목록
        try {
            return hospitalMapper.reservationList(hospitalId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약목록 불러오기 실패");
        }
    }

    @Override
    public List<ReservationInfoDto> reservationDateList(HospitalAndDateDto haDto) { //해당 날짜 예약 목록
        try {
            return hospitalMapper.reservationDateList(haDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약목록 불러오기 실패");
        }
    }

    @Override //예약 상세 정보
    public ReservationInfoDto reservationInfo(HospitalAndReservation haDto) {
        try {
            ReservationInfoDto rDto = hospitalMapper.reservationInfo(haDto).orElseThrow(
                    () ->  new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약 상세 정보 실패" ));
            return rDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약 상세 정보 실패");
        }
    }

    @Override //의사 상세 정보
    public DoctorDetailDto doctorDetail(String doctorId) {

        DoctorDetailDto dto = new DoctorDetailDto();

        try {
            dto = hospitalMapper.doctorDetail(doctorId).orElseThrow(
                    () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패")
            );
            String base64Img = common.convertImageToBase64(dto.getImgFile());

            CountDto countDto = hospitalMapper.doctorCount(doctorId).orElseThrow(
                    () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패")
            );

            dto.count(base64Img, countDto.getCounselingCount(),countDto.getLikeCount());

            return dto;
        }catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패");
        }

    }

}