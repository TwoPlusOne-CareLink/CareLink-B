package com.careLink.member.service;

import com.careLink.auth.mapper.AuthMapper;
import com.careLink.common.Common;
import com.careLink.entity.*;
import com.careLink.exception.ErrorException;
import com.careLink.member.dto.*;
import com.careLink.member.mapper.CareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//병원찾기 && 헬스 케어(일일체크 진단)
@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CareServiceImpl implements CareService {

    private final CareMapper careMapper;
    private final Common common;
    private final AuthMapper authMapper;
    @Override //병원 전체 목록 & 검색
    public List<HospitalInfoDto> hospitalInfo(String hospitalName) {


        try {
            List<HospitalEntity> list = careMapper.hospitalSelect(hospitalName); //전체 병원 목록 불러오기
            List<HospitalInfoDto> hInfo = new ArrayList<>(); //리턴해줄 타입

            for(HospitalEntity he : list) { //위도와 경도를 하나의 객체에 담아 전송하기 위해 위에서 list로 불러온 같은 값이지만 한번 더 실행
                LatLngDto latLngDto = new LatLngDto(he.getLat(), he.getLng()); //따로따로 있는 위도와 경도를 하나의 객체에 담기
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
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 정보 찾기 실패"); //errorCode: 400
        }
        
    }

    //hDetail 여기서 사용
    public HospitalDetailDto detail(int hosptialId) { //병원정보(+진료과목)
        try {
            return careMapper.hospitalInfo(hosptialId); //병원 상세 정보를 리턴
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원상세정보 병원정보(진료과목) 실패"); //errorCode: 400
        }
    }

    public int hospitalLikeCount(int hospitalId) { //병원 좋아요 개수 조회
        try {
            return careMapper.hospitalLikeCount(hospitalId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 좋아요 계산 실패"); //errorCode: 400
        }
    }

    @Override
    public HospitalDetailResultDto hDetail(int hospitalId) { //병원상세정보(병원정보,진료과목,의사정보,좋아요수)
        
        //병원정보
        HospitalDetailDto hospitalDetailDto = detail(hospitalId);
        
        //문자열로 받아온 진료과목들을 배열로 변환
        String[] departName = hospitalDetailDto.getDepartmentNames().split(",");
        
        //진료과목을 리스트에 담기
        List<String> departmentNames = new ArrayList<>();

        for(String name : departName) {
            departmentNames.add(name);
        }

        //위도 경도 LatLngDto에 담기
        LatLngDto latlng = new LatLngDto(hospitalDetailDto.getLat(),hospitalDetailDto.getLng());
        
        List<DoctorProfileDto> doctorProfileDtos = new ArrayList<>();
        //의사정보
        try {
            doctorProfileDtos = careMapper.doctorProfile(hospitalId); //DB에서 의사정보를 조회해서 doctorProfileDtos에 담기
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원상세정보 의사정보 실패");
        }

        //의사들의 좋아요 합 = 병원 좋아요
        int totalLike = hospitalLikeCount(hospitalId); //병원 좋아요 개수

        for(DoctorProfileDto dto : doctorProfileDtos) {
            String img = common.convertImageToBase64(dto.getImgFile()); //byte -> base64
            dto.base64Img(img);
        }


        HospitalDetailResultDto hospitalDetailResultDto = new HospitalDetailResultDto(
                                                                hospitalDetailDto.getHospitalId(), //병워 번호
                                                                hospitalDetailDto.getHospitalName(), //병원 이름
                                                                hospitalDetailDto.getAddress(), //주소
                                                                hospitalDetailDto.getWeekdayOpeningtime(), //오픈시간
                                                                hospitalDetailDto.getWeekendOpeningtime(), //마감시간
                                                                hospitalDetailDto.getTel(), //전화번호
                                                                hospitalDetailDto.getHolidayCheck(), //주말 진료 여부
                                                                hospitalDetailDto.getLunchHour(), //점심시간
                                                                latlng, //위도,경도
                                                                totalLike, //총 좋아요수(소속의사들의 총 좋아요수)
                                                                departmentNames, //진료과목들(리스트)
                                                                doctorProfileDtos//의사정보들 (리스트)
                                                            );
        return hospitalDetailResultDto;
    }

    @Override
    public int Reservation(ReservationEntity reservationEntity) { //병원 예약

        int dateCheck = dateCheck(reservationEntity);

        if(dateCheck>0) { //예약 중복
            log.info("당일이미 예약함");
            throw new ErrorException(HttpStatus.CONFLICT.value(), "중복된 예약이 존재"); //errorCode:409
        }
        
        //에약한 진료 날짜에 해당 진료과목 시간에 예약이 있는지 중복 확인
        int check = ReservationCheck(reservationEntity);

        if(check>0) { //예약 중복
            log.info("다른사람이 예약되어있음");
            throw new ErrorException(HttpStatus.CONFLICT.value(), "중복된 예약이 존재"); //errorCode:409
        }

        //예약 중복x  예약 진행하기
        try { //예약하기
            careMapper.reservation(reservationEntity);
            return reservationEntity.getReservationId(); //예약번호 리턴
        } catch(ErrorException e) {
            throw e;//중복된 예약에 대한 예외처리 종료
        } catch(Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약 실패"); //errorCode:400
        }
    }

    public int dateCheck(ReservationEntity reservationEntity) { //예약중복확인(당일 1일 1예약 원칙)
        try {
            return careMapper.dateCheck(reservationEntity); //예약조회를 해서 예약된수를 리턴
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 예약 중복 확인 실패"); //errorCode:400
        }
    }

    public int ReservationCheck(ReservationEntity reservationEntity) { //예약 중복 확인
        try {
            return careMapper.check(reservationEntity); //같은날, 같은과목, 같은 시간에 예약된 내역이 잇는지 개수 조회
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원 예약 중복 확인 실패"); //errorCode:400
        }
    }

    @Override //나의 예약 전체 내역
    public List<SelectMyReservationDto> getMyReservation(String memberId) {
        try {
            List<SelectMyReservationDto> selectMyReservationDto = careMapper.selectMyReservation(memberId); //memberId로 예약된 모든 에약내역 조회
            return selectMyReservationDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "나의 예약 목록 받아오기 실패"); //errorCode:400
        }
    }

    @Override //나의 예약일 예약 내역 조회
    public SelectMyReservationDto getMyReservationDetail(MyReservationDetailDto myReservationDetailDto) {
        try {
            SelectMyReservationDto selectMyReservationDto = careMapper.getMyReservationDetail(myReservationDetailDto).orElseThrow(
                    () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약일 예약내역 조회 예외처리 조회는되지만 빈값") //errorCode:400
            );
            return selectMyReservationDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "예약일 예약 내역 조회 실패"); //errorCode:400
        }
    }

    @Override
    public ReservationPageDto reservationPageInfo(ReservationDto dto) { //병원 예약 페이지


        //회원정보 + 해당 병원 진교과목 정보 -> 우리는 조인으로 했는데 조인말고 select문을 따로 하나 더 사용해서 진료과목&과목명 따로 출력해도 됨
        ReservationDefaultDto rdDto = reservationDefaultInfo(dto);

        String[] departmentIds = rdDto.getDepartmentIds().split(",");
        String[] departmentNames = rdDto.getDepartmentNames().split(",");

        List<DepartmentDto> dList = new ArrayList<>();
        DepartmentDto dDto;
        dDto = new DepartmentDto(0,"진료과목 선택");
        dList.add(dDto);

        for(int i=0; i<departmentIds.length; i++) {
            dDto = new DepartmentDto(Integer.parseInt(departmentIds[i]), departmentNames[i]); //진료과목번호화 진료과목명을 새로운 dDto 생성자에 넣어서 아래 리스트에 담기
            dList.add(dDto);
        }

        try {
            //병원예약정보 불러오기
            List<ReservationPageInfoDto> pageList = careMapper.dateInfo(dto.getHospitalId());
            return new ReservationPageDto(rdDto.getMemberName(), rdDto.getMemberTel(), dList, pageList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원예약 페이지 회원정보 불러오기 실패"); //errorCode:400
        }
    }

    public ReservationDefaultDto reservationDefaultInfo(ReservationDto dto) {//회원이름, 회원전화번호, 병원 진료과목 정보들

        try {
            ReservationDefaultDto reservationDefaultDto = careMapper.rDefaultInfo(dto).orElseThrow(
                    () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "로그인은 했는데 db에 회원정보가 없다.(원인모르는 에러발생)") //errorCode:400
            );
            return reservationDefaultDto;

        }catch (Exception e) {
            e.printStackTrace();
            throw  new ErrorException(HttpStatus.BAD_REQUEST.value(), "병원예약페이지 기본정보 불러오기 실패"); //errorCode:400
        }

    }

    @Override //예약 취소
    public void reservationDelete(int reservationId) {
        try {
            careMapper.reservationDelete(reservationId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패"); //errorCode:400
        }

    }

    @Override //체크리스트 작성 & 목록 페이지
    public MemberInfoDto HealthCheckListPage(String memberId) {
        //회원정보 받아오기
        MemberEntity member = authMapper.findById(memberId).orElseThrow(
                () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "회원정보 없음")
        );

        //회원 나이, 성별 계산 --------------------------------------------------
        int gender = Integer.parseInt(member.getGender());  //1
        String age = member.getAge(); //910917
        int memberAge = common.age(gender,age);

        String memberGender = (gender%2) == 0 ? "여자" : "남자";

        //------------------------------------------------------------------------
        try {
            List<CheckListInfoDto> list = careMapper.checkListPage(memberId);
            return new MemberInfoDto(memberId,
                                     member.getMemberName(),
                                     memberAge, memberGender, list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "체크리스트 항목 조회 실패"); //errorCode:400
        }

    }

    @Override //체크리스트 작성
    public int HealthCheckAdd(HealthCheckEntity healthCheckEntity) {
        String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy/MM/dd")); //날짜 형식
        DuplicateCheckDto dto = new DuplicateCheckDto(healthCheckEntity.getMemberId(), currentYear);

        int check = duplicateCheck(dto);

        if(check == 1) {
            throw new ErrorException(HttpStatus.CONFLICT.value(), "오늘은 이미 작성했습니다."); //errorCode:409
        }

        try {
            careMapper.checkListAdd(healthCheckEntity);
            return healthCheckEntity.getCheckId();
        }catch(ErrorException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "등록 실패"); //errorCode:400
        }

    }

    public int duplicateCheck(DuplicateCheckDto dto) {
        try {
            return careMapper.duplicateCheck(dto);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "중복체크 검사 실패"); //errorCode:400
        }
    }

    @Override //체크리스트 작성 내역(결과)
    public CheckListResultDto ckResult(int checkId) {
        CheckListResultDto checkListResultDto;
        try {
            checkListResultDto = careMapper.checkResult(checkId).orElseThrow(
                    () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "작성내역 조회 실패") //errorCode:400
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "실패"); //errorCode:400
        }

        String gender = checkListResultDto.getGender(); //성별
        int age = checkListResultDto.getAge(); //나이
        double height = checkListResultDto.getHeight(); //신장
        double weight = checkListResultDto.getWeight(); //체중
        int heartRate = checkListResultDto.getHeartRate(); //심박수
        int bloodSugar = checkListResultDto.getBloodSugar(); //혈당지수
        int systolicBloodPressure = checkListResultDto.getSystolicBloodPressure(); //수축기 혈압
        int relaxationBloodPressure = checkListResultDto.getRelaxationBloodPressure(); //이완혈압

        //bmi 구하기
        double h = height/100; //키를 m로 환산
        double bmi = weight/(h*h); //체중/키(m)*키(m)
        String bmiResult = common.bmiResult(bmi);

        //혈압 결과 구하기
        String bpResult = common.bloodPressure(systolicBloodPressure, relaxationBloodPressure); //혈압 결과

        //심박수 결과 구하기
        String hrResult = common.hrResult(gender, age, heartRate); //심받수 결과

        //혈당 결과 구하기
        String bsResult =  common.bsResult(bloodSugar); //혈당 결과;

        checkListResultDto.result(bpResult, bsResult, hrResult, bmiResult);

        return checkListResultDto;
    }

    @Override // 질병백과
    public List<DiseaseEntity> diseaseList() {
        log.info("질병백과 서비스");
        try {
            return careMapper.diseaseList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "질병백과 조회 실패"); //errorCode:400
        }
    }

    @Override //질병 상세 정보
    public DiseaseEntity diseaseDetail(int diseaseId) {
        try {
                return careMapper.diseaseDetail(diseaseId).orElseThrow(
                        () -> new ErrorException(HttpStatus.BAD_REQUEST.value(), "질병 조회 실패(1)") //errorCode:400
                );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(HttpStatus.BAD_REQUEST.value(), "질병 조회 실패(2)"); //errorCode:400
        }
    }

}
