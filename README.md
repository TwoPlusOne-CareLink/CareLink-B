
<img src="https://ifh.cc/g/6AnThw.jpg" />

# 👨‍⚕️ 비대면 상담서비스 CareLink

----

## 👨‍⚕️ 프로젝트 소개
비대면 상담을 통해 오프라인의 제약을 극복하고, 온라인에서도 손쉽게 전문의료진과 소통하여 의료상담과 정보를 얻을 수 있는 서비스

#### [Github-Front](https://github.com/TwoPlusOne-CareLink/CareLink-F)
#### [Github-Back](https://github.com/TwoPlusOne-CareLink/CareLink-B)

----

## ⚙️서비스아키텍처
<img src="https://ifh.cc/g/SgqXSL.png" />

----

## 📅프로젝트기간
2023년 10월 28일 ~ 2023년 12월 08일

----
## ✨주요기능

### 일반회원
|페이지|API 및 관련 메서드|
|---|---|
|로그인|JWT 토큰 발급을 통한 로그인|
|메인페이지|일반회원, 의사, 병원관계자 별 다른 메인페이지 진입 및 기능페이지 이동|
|병원찾기|카카오 map api를 사용하여 지도 내 전체 병원, 내 위치 기반 병원 조회가능<br>특정 병원 검색 및 상세정보 확인<br>병원 진료예약현황 확인 가능 및 진료예약|
|비대면 상담|비대면 상담 폼에 맞춰 비대면 상담 작성 및 상세정보확인가능<br>의사 답변 달리지 않으면 달리지 않은 메시지 확인 및 답변 달릴시 의사 답변 확인가능|
|헬스케어|건강 일일체크리스트 작성 가능 및 검사결과 확인가능<br>검사결과는 bmi, 혈압, 혈당,심박수 및 측정 기준 확인가능 |
|질병백과|등록되어있는 질병 리스트로 조회 및 상세정보 확인(정의, 원인, 증상, 진단, 치료)|
|마이페이지|나의 진료 예약내역 캘린더로 확인 및 해당 날짜 클릭시 상세정보 확인 가능<br>회원정보 수정 가능|

### 의사
|페이지|API 및 관련 메서드|
|---|---|
|로그인|JWT 토큰 발급을 통한 로그인|
|비대면 상담목록|일반회원이 등록한 답변이 달리지 않은 상담글 확인 및 답변가능|
|나의 상담내역|답변 남긴 글 상세정보 확인 가능|

### 병원관계자
|페이지|API 및 관련 메서드|
|---|---|
|로그인|JWT 토큰 발급을 통한 로그인|
|의사목록조회|해당 병원에 속한 전체 의사 리스트로 확인 및 상세정보 확인 가능|
|예약내역조회|캘린더로 병원에 예약되어있는 전체 내역조회 및 날짜 클릭 시 해당날짜 예약 리스트 확인 가능 <br> 리스트 내 진료상세정보 조회가능 |

----

## 🗺API 설계
### [API설계 보러가기](https://www.notion.so/38be19feec0143ca886808fb65e56fc2?v=7ddb449397d5416d9b228caf71b9c96f)

----
## 🔨기술적의사결정

|적용 기술|적용 목적|
|---|---|
|리액트 리덕스 툴킷|redux의 문제점인 액션,스토어, 리듀서 반복작성으로 인하여 발생하는 보일러플레이트코드 및 가독성을 해결하고자 redux-toolkit에 내장되어있는 createSlice 함수를 사용하여 문제점 해결하고자 함|
|리액트 풀 캘린더|진료예약 관련 원활한 일정 관리를 위하여 사용 | 
|액시오스|데이터 통신간 서버에 요청시 header 부분에 토큰을 담아서 보내기위해 내장된 기능인 interceptors를 사용하기 위함|response를 받을때 json 타입을 자동으로 변환받기 위함|

----
## 🔨 사용한 기술스택


### ✔️Frond-end
<img src="https://img.shields.io/badge/HTML-red?style=for-the-badge&logo=HTML&logoColor=black"><img src="https://img.shields.io/badge/Css-blue?style=for-the-badge&logo=Css&logoColor=black"><img src="https://img.shields.io/badge/javascript-orange?style=for-the-badge&logo=javascript&logoColor=yellow"><img src="https://img.shields.io/badge/styled-components-orange?style=for-the-badge&logo=styled-components&logoColor=yellow">
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=black"><img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=purple"><img src="https://img.shields.io/badge/Reduxtoolkit-764ABC?style=for-the-badge&logo=Reduxtoolkit&logoColor=purple"><img src="https://img.shields.io/badge/fullcalendar-000000?style=for-the-badge&logo=fullcalendar&logoColor=white">
### ✔️Bac/k-end
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=yellow"><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=yellow"><img src="https://img.shields.io/badge/Oracle-white?style=for-the-badge&logo=Oracle&logoColor=red"><img src="https://img.shields.io/badge/jsonwebtokens-white?style=for-the-badge&logo=jsonwebtokens&logoColor=purple">

----

## 👨‍👩‍👧팀원소개

|이름|깃허브 주소|포지션|
|---|---|---|
|이진호|[Jino0403의 github](https://github.com/Jino0403)|FrontEnd|
|정성민|[jsms91의 github](https://github.com/jsms91)|BackEnd|
|이승진|[2seungjin의 github](https://github.com/2seungjin)|BackEnd|






