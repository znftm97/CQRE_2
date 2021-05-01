# 스프링 부트로 만든 게시판+쇼핑몰
## 배포 URL
- ec2-15-165-245-31.ap-northeast-2.compute.amazonaws.com:8080
## 배포환경
- AWS EC2 (Ubuntu 20.04)
- AWS RDS (Mysql)
- AWS S3
- Docker
## 사용기술
- JAVA8
- Thymeleaf
- jQuery
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data Jpa
- Querydsl
## Build tool
- Gradle
## 기능
- 인증, 회원 관련
  - 이메일인증(SMTP)를 통한 회원가입
  - 로그인, 로그아웃, OAuth2 로그인
  - ID찾기, 비밀번호찾기, 회원정보 수정
  - 나의 글, 댓글, 주문목록, 주문취소 목록, 장바구니 목록, 쿠폰 조회
- 인가(Admin 권한)
  - 공지사항 글쓰기
  - 쿠폰 생성, 회원에게 쿠폰 전송
- 게시판
  - 글 CRUD
  - AJAX 댓글, 대댓글 CRUD
  - AJAX 글 좋아요 버튼
  - 조회수, 추천수에 따른 정렬
  - 글 검색  
- 갤러리
  - AWS S3를 이용한 이미지 업로드, 다운로드, 조회, 삭제
- 쇼핑몰
  - 카테고리별 상품 조회
  - 상품 CRUD
  - 상품 주문, 주문취소, 재 주문, 장바구니
  - 쿠폰 사용 주문