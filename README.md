# CQRE  
교내 학술동아리를 위한 애플리케이션이라는 주제로, 게시판과 쇼핑몰 기능을 구현한 개인 프로젝트입니다.<br>

![image](https://user-images.githubusercontent.com/57134526/136526132-d32b946d-9092-412d-881f-7aa54e643f04.png)

## 📂 CQRE Wiki
[CQRE 프로젝트 Wiki - 1](https://languid-visage-6fe.notion.site/CQRE-Wiki-1-d0748dea60e84379869f7c2404a427c6)<br>
[CQRE 프로젝트 Wiki - 2](https://languid-visage-6fe.notion.site/CQRE-Wiki-2-2996c9ddea484275b89d0d0b14022774)

## 🏠 배포 URL
http://ec2-54-180-142-70.ap-northeast-2.compute.amazonaws.com:8080/

## 🔧 배포 환경
- AWS EC2 (Ubuntu 20.04)
- AWS RDS (MySQL 8.0.23)
- AWS S3
- Docker Hub
- GitHub Action
- Jenkins

## 📚 사용 기술
- Thymeleaf, JQuery, SCSS
- JAVA11<br>
- Spring Boot<br>
- Spring MVC<br>
- Spring Security<br>
- Spring Data Jpa<br>
- Querydsl<br>

## 🔱 ERD
<img src="https://user-images.githubusercontent.com/57134526/119619566-19343100-be3f-11eb-89a6-1d10aab5933b.png"></img>

## 🔁 CI/CD 파이프라인 (배포 자동화)
![cqre_CICD](https://user-images.githubusercontent.com/57134526/136551080-3baef7b4-96ba-47a4-a266-45156d18c168.png)<br>
- [구축 과정 정리](https://languid-visage-6fe.notion.site/CQRE-Wiki-2-2996c9ddea484275b89d0d0b14022774)


## 🔎 구현 기능
<details>

- **인증, 회원 관련**
  - 이메일인증(SMTP)를 통한 회원가입
  - 로그인, 로그아웃, OAuth2 로그인(카카오, 네이버, 구글, 페이스북)
  - ID찾기, 비밀번호찾기, 회원정보 수정
  - 나의 글, 댓글, 주문목록, 주문취소 목록, 장바구니 목록, 쿠폰 조회 (나의 정보 페이지)
- **인가(Admin 권한)**
  - 공지사항 글쓰기
  - 쿠폰 생성, 회원에게 쿠폰 발급
- **게시판**
  - 글 CRUD
  - 비동기 댓글, 대댓글 CRUD
  - 비동기 글 좋아요 버튼
  - 조회수, 추천수에 따른 정렬
  - 글 검색
- **갤러리**
  - AWS S3를 이용한 이미지 업로드, 다운로드, 조회, 삭제
- **쇼핑몰**
  - 카테고리별 상품 조회
  - 상품 CRUD
  - 상품 주문, 주문취소, 재 주문, 장바구니
  - 쿠폰 적용한 주문
</details>
  
