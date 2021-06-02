# 스프링 부트로 만든 게시판+쇼핑몰
## 배포 URL
- http://ec2-15-165-245-31.ap-northeast-2.compute.amazonaws.com:8080
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
## ERD
![image](https://user-images.githubusercontent.com/57134526/119619566-19343100-be3f-11eb-89a6-1d10aab5933b.png)
## 이슈 및 해결
### 1. 동기화
- 해당 주제로 [블로그](https://velog.io/@znftm97/%EC%8A%A4%ED%94%84%EB%A7%81%EC%97%90%EC%84%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0-tl5imu04)에 자세히 포스팅하였습니다.
- **상황**
    - 대댓글, 갤러리 이미지, 쇼핑몰 이미지 기능 구현 시 그룹으로 묶기위해 
    서비스 레이어에서 Long타입의 bundleId라는 전역 멤버를 사용했습니다.
- **이슈**
    - 서비스 클래스는 싱글톤 객체이므로 전역 멤버 사용시 
    여러 쓰레드에서 접근 가능한 공유자원이므로 동기화가 필요합니다.
- **해결**
  - synchronized 키워드 사용
    - `Comment`

      ```java
      public class Comment extends BaseEntity {
          ...
          private Long bundleId;

          ...

          public void setBundleId(AtomicLong bundleId) {
              this.bundleId = bundleId.getAndIncrement();
          }
      }
      ```

    - `CommentService`

      ```java
      /*댓글 생성*/
      @Transactional
      public void createComment(CreateCommentDto dto) {
          Post findPost = postRepository.findById(dto.getPostId()).orElseThrow(CPostNotFoundException::new);
          User loginUser = userService.getLoginUser();

          Comment comment = Comment.builder()
                      .content(dto.getContent())
                      .user(loginUser)
                      .post(findPost)
                      .depth(1)
                      .bundleOrder(System.currentTimeMillis())
                      .existsCheck(true)
                      .build();

          synchronized (bundleId) {
              comment.setBundleId(bundleId);
          }

          commentRepository.save(comment);
      }
      ```

      - bundleId를 연산하는 부분만 뽑아서 synchronized 코드 블럭 영역에 넣었습니다.
  
  - AtomicLong 클래스 사용
    - `CommentService`

      ```java
      @Service
      @RequiredArgsConstructor
      public class CommentService {
          AtomicLong bundleId = new AtomicLong(1);

          private final CommentRepository commentRepository;
          private final PostRepository postRepository;
          private final UserService userService;

          ...

          /*댓글 생성*/
          @Transactional
          public void createComment(CreateCommentDto dto) {
              Post findPost = postRepository.findById(dto.getPostId()).orElseThrow(CPostNotFoundException::new);
              User loginUser = userService.getLoginUser();

              Comment comment = Comment.builder()
                      .content(dto.getContent())
                      .user(loginUser)
                      .post(findPost)
                      .depth(1)
                      .bundleId(bundleId.getAndIncrement())
                      .bundleOrder(System.currentTimeMillis())
                      .existsCheck(true)
                      .build();

              commentRepository.save(comment);
          }
      ```

      - AtomicLong 클래스는 내부적으로 volatile 키워드와 CAS 알고리즘을 통해 동기화를 지원합니다.
      - 기본값을 1로 설정 후 생성하고, omment 생성시 getAndIncrement 메서드를 통해 값을 가져온 후 1을 더해줍니다.
      - 결론은 synchronized는 bundleId에 접근시 Lock이 걸리므로 성능에 영향이 있습니다. 따라서 이처럼 간단한 연산을 수행하는 경우
        Atomic 클래스로 간단히 해결할 수 있습니다.
        
### 2. 이미지 중복 제거 조회 쿼리
- **상황**
    - 하나의 갤러리 글을 생성할 때 여러 이미지 파일이 업로드 됩니다. 갤러리 글 목록을 
    조회 할 때는 여러 이미지 파일 중 대표로 하나의 이미지만 필요로합니다.

        where절 서브쿼리를 활용해 같은 bundleId를 가지는 이미지 파일 중 대표로 
        하나의 이미지 파일만 조회하는 네이티브 쿼리를 작성 후 Querydsl로 구현합니다.

- **이슈**
    - Querydsl where절에서 두 개의 파라미터를 넘기는 경우 컴파일 에러가 발생했습니다.
    - `GalleryRepositoryImpl`

            ```java
            jpaQueryFactory
              .selectFrom(galleryFile)
              .where((galleryFile.bundleId, galleryFile.bundleOrder).in(
                      JPAExpressions
                          .select(galleryFileSub.bundleId, galleryFileSub.bundleOrder.min())
                          .from(galleryFileSub)
                          .groupBy(galleryFileSub.bundleId)
              ))
              .fetch();
            ```

        - where절에서 두 개 이상의 파라미터를 사용할 경우 컴파일 에러가 났습니다. (하나의 파라미터만 사용 시 에러가 안났습니다.)
        - 에러 이유
            - DB에따라 멀티 컬럼 서브쿼리를 지원여부가 다르다고 합니다. H2 DB가 멀티 컬럼 서브쿼리를 지원하지 않아서 또는
            DB에따라 동작여부가 달라져 무조건 컴파일시 에러를 발생 시키는건지 두 이유 중 한 이유로 컴파일 에러가 발생한 것 같습니다.
            따라서 이런 경우에는 네이티브 쿼리를 사용하거나, 쿼리를 두 번 나누어 실행해야 합니다.
            
- **해결**
    - 쿼리를 두 번으로 나눠 해결했습니다.
    - `GalleryRepositoryImpl`
      ```java
      @RequiredArgsConstructor
      public class GalleryRepositoryImpl implements GalleryRepositoryCustom{

      private final JPAQueryFactory jpaQueryFactory;

      @Override
      public Page<FindGalleryFileDto> findAllDistinctByBundleId(Pageable pageable) {
          List<FindGalleryFileDistinctDto> fetch = jpaQueryFactory
                  .select(new QFindGalleryFileDistinctDto(
                          galleryFile.bundleId,
                          galleryFile.id.min()
                  ))
                  .from(galleryFile)
                  .groupBy(galleryFile.bundleId)
                  .fetch();

          List<Long> collect = fetch.stream()
                  .map(f -> f.getId())
                  .collect(Collectors.toList());

          QueryResults<FindGalleryFileDto> result = jpaQueryFactory
                  .select(new QFindGalleryFileDto(
                          galleryFile.id,
                          galleryFile.title,
                          galleryFile.filePath,
                          galleryFile.createDate,
                          QUser.user.name,
                          galleryFile.bundleId,
                          galleryFile.bundleOrder))
                  .from(galleryFile)
                  .where(galleryFile.id.in(collect))
                  .leftJoin(galleryFile.user, QUser.user)
                  .offset(pageable.getOffset())
                  .limit(pageable.getPageSize())
                  .orderBy(galleryFile.bundleId.asc())
                  .fetchResults();

          List<FindGalleryFileDto> content = result.getResults();
          long total = result.getTotal();

          return new PageImpl<>(content, pageable, total);
      }
      ```
    1. 여러 이미지 파일들을 bundleId를 기준으로 중복 제거 후 조회하는 첫번째 쿼리를 작성
    2. 첫번째 쿼리에서 Stream API를 이용해 id(PK)값만 뽑아 List를 생성
    3. 모든 이미지를 조회하는 쿼리의 where in 절에서 2번에서 id값만 뽑은 List를 넣어 원하는 이미지만 조회합니다.
       
### 3. OAuth2 로그인 문제
  - **이슈**
      - User 엔티티로 캐스팅할 수 없다는 에러가 발생합니다.

          ```java
          /*로그인 사용자 조회*/
          public User getLoginUser() {
              User loginUser;

              try {
                  loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
              } catch (ClassCastException e) {
                  throw e;
              }
              return userRepository.CFindById(loginUser.getId()).orElseThrow(CUserNotFoundException::new);
          }
          ```

        - 디버깅 결과 OAuth2로 로그인한 경우 SecurityContext에 DefaultOAuth2User 객체가 들어가서 User 클래스로 캐스팅할 수 없기 때문에 캐스팅에러가 발생하는것을 확인 했습니다.
      
  - **해결**
      - `UserService`

          ```java
          /*로그인 사용자 조회*/
          public User getLoginUser() {
              Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

              /*일반 로그인 사용자*/
              if (principal instanceof User) {
                  User loginUser = (User) principal;
                  return userRepository.findByEmail(loginUser.getEmail());

              /*OAuth2 로그인 사용자*/
              } else {
                  DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
                  Map<String, Object> attributes = defaultOAuth2User.getAttributes();

                  /*카카오는 이중 Map 구조라 다르게 구현*/
                  if (Objects.isNull(attributes.get("email"))) {
                      Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
                      return userRepository.findByEmail(kakao_account.get("email").toString());
                  } else {
                      return userRepository.findByEmail(attributes.get("email").toString());
                  }
              }
          }
          ```

          - SecurityContextHolder에서 가져온 principal이 User클래스인 경우와, DefaultOAuth2User인 경우 두 경우로 나눠 처리했습니다.
          - 비교할때는 클래스의 이름을 String으로 가져와서 equals 메서드로 비교했습니다.
            1. 일반 로그인 사용자인 경우 SecurityContextHolder에서 가져온 principal을 User클래스로 형변환 후, DB에서 조회후 리턴했습니다.
            2. OAuth2 로그인 사용자인 경우 ecurityContextHolder에서 가져온 principal을 DefaultOAuth2User 클래스로 형변환 후, DB에서 조회 후 리턴했습니다.
              - 카카오의 경우 필요한 사용자 이름, 메일 같은 정보가 Map 안에  kakao_account라는 Map안에 들어있어서 별도로 분기 했습니다.

### 4. AWS EC2 연결성 검사 실패
  - **이슈**
    - 배포 후 ec2를 실행시키고 일정 시간이 지나면 ec2에 접근이 불가능한 현상이 발생했습니다.
    - CPU 사용량을 보니 99.9%를 찍고 메모리가 부족해서 생긴 현상이라는 걸 알았습니다. AWS 프리 티어를 사용하다보면 자주 발생하는 이슈라고 합니다.

  - **해결**
      - SWAP 메모리 사용
          - 메모리가 부족할 경우 HDD의 일정공간을 마치 RAM처럼 사용합니다.
          1. swap 메모리 할당

              ```bash
              sudo dd if=/dev/zero of=/swapfile bs=128M count=16
              ```

          2. swap 파일에 대한 읽기,쓰기 권한 업데이트

              ```bash
              sudo chmod 600 /swapfile
              ```

          3. swap 영역 설정

              ```bash
              sudo mkswap /swapfile
              ```

          4. 스왑 공간에 스왑파일 추가

              ```bash
              sudo swapon /swapfile
              ```

          5. 부팅 시 스왑파일 활성화 설정

              ```bash
              sudo vi /etc/fstab
              ```

              - 파일 끝에 아래 명령어 추가

                  ```bash
                  /swapfile swap swap defaults 0 0
                  ```

      - 이후 cpu가 100%를 찍는 경우가 발생해도 인스턴스가 뻗어버리는 현상은 발생하지 않았습니다.
