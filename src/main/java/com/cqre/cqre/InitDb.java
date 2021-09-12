package com.cqre.cqre;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.domain.GalleryFile;
import com.cqre.cqre.domain.User;
import com.cqre.cqre.domain.post.Board;
import com.cqre.cqre.domain.post.Post;
import com.cqre.cqre.domain.shop.Coupon;
import com.cqre.cqre.domain.shop.ItemImage;
import com.cqre.cqre.domain.shop.item.Category;
import com.cqre.cqre.domain.shop.item.CommonItem;
import com.cqre.cqre.domain.shop.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.init();
        initService.categoryInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void init() {
            User user1 = User.builder()
                    .name("아무개")
                    .studentId("20202020")
                    .loginId("znftm97")
                    .password(passwordEncoder.encode("wprkfrhdaud12!"))
                    .email("test1@gmail.com")
                    .emailVerified("false")
                    .emailCheckToken(UUID.randomUUID().toString())
                    .role("ROLE_USER")
                    .build();

            User user2 = User.builder()
                    .name("홍길동")
                    .studentId("12341234")
                    .loginId("123")
                    .password(passwordEncoder.encode("123"))
                    .email("test2@gmail.com")
                    .emailVerified("false")
                    .emailCheckToken(UUID.randomUUID().toString())
                    .role("ROLE_USER")
                    .build();

            User admin = User.builder()
                    .name("관리자")
                    .loginId("admin")
                    .studentId("admin")
                    .password(passwordEncoder.encode("1234"))
                    .role("ROLE_ADMIN")
                    .email("admin")
                    .emailVerified("false")
                    .emailCheckToken(UUID.randomUUID().toString())
                    .build();

            user1.updateEmailVerified();
            user2.updateEmailVerified();
            admin.updateEmailVerified();
            em.persist(user1);
            em.persist(user2);
            em.persist(admin);

            Coupon coupon1 = createCoupon(10, 50L, "연말기념 쿠폰", admin);
            Coupon coupon2 = createCoupon(10, 40L, "연초기념 쿠폰", admin);
            Coupon coupon3 = createCoupon(50, 30L, "크리스마스기념 쿠폰", admin);
            Coupon coupon4 = createCoupon(100, 20L, "생일 쿠폰", admin);
            Coupon coupon5 = createCoupon(1000, 10L, "그냥 쿠폰", admin);

            em.persist(coupon1);
            em.persist(coupon2);
            em.persist(coupon3);
            em.persist(coupon4);
            em.persist(coupon5);

            Post post1 = createPost("1111", "1111", 55, 16, user1, Board.FREE);
            Post post2 = createPost("2222", "2222", 21, 12, user1, Board.FREE);
            Post post3 = createPost("3333", "3333", 32, 13, user1, Board.FREE);
            Post post4 = createPost("4444", "4444", 10, 3, user1, Board.FREE);
            Post post5 = createPost("5555", "5555", 22, 5, user2, Board.FREE);
            Post post6 = createPost("6666", "6666", 19, 4, user2, Board.FREE);
            Post post7 = createPost("7777", "7777", 5, 1, user2, Board.FREE);
            Post post8 = createPost("공지사항1", "ㅁㄴㅇㅇㄹ", 11, 3, admin, Board.NOTICE);
            Post post9 = createPost("공지사항2", "ㅍㅊㅍㅊ", 24, 8, admin, Board.NOTICE);
            Post post10 = createPost("공지사항3", "ㅁㄴㅇㅁㄴㅇ", 15, 2, admin, Board.NOTICE);
            Post post11 = createPost("공지사항4", "ㅇㄴㅁㅇㅁㄴㅇㅁㄴ", 4, 2, admin, Board.NOTICE);
            Post post12 = createPost("공지사항5", "22ㅇㄴㅁㅇ22", 3, 1, admin, Board.NOTICE);
            Post post13 = createPost("공지사항6", "ㄹㄹㄹ", 4, 2, admin, Board.NOTICE);
            Post post14 = createPost("공지사항7", "22ㄹㄹ22", 8, 6, admin, Board.NOTICE);

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);
            em.persist(post9);
            em.persist(post10);
            em.persist(post11);
            em.persist(post12);
            em.persist(post13);
            em.persist(post14);


            String filePath1 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-canola-4029105_1920.jpg";
            String filePath2 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-caravansary-4519442_1920.jpg";
            String filePath3 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-loveourplanet-4851331_1920.jpg";
            String filePath4 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-plane-841441_1920.jpg";
            String filePath5 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-prague-3010407_1920.jpg";
            String filePath6 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/galleryImages/temp-volkswagen-569315_1920.jpg";


            GalleryFile galleryFile1 = createGalleryFile("test", "test", "test", filePath1, user2, -10L, 111111L);
            GalleryFile galleryFile2 = createGalleryFile("test", "test", "test", filePath2, user2, -10L, 111111L);
            GalleryFile galleryFile3 = createGalleryFile("test", "test", "test", filePath3, user2, -10L, 111111L);
            GalleryFile galleryFile4 = createGalleryFile("test", "test", "test", filePath2, user2, -20L, 111111L);
            GalleryFile galleryFile5 = createGalleryFile("test", "test", "test", filePath4, user2, -20L, 111111L);
            GalleryFile galleryFile6 = createGalleryFile("test", "test", "test", filePath5, user2, -20L, 111111L);
            GalleryFile galleryFile7 = createGalleryFile("test", "test", "test", filePath3, user2, -30L, 111111L);
            GalleryFile galleryFile8 = createGalleryFile("test", "test", "test", filePath4, user1, -40L, 111111L);
            GalleryFile galleryFile9 = createGalleryFile("test", "test", "test", filePath5, user1, -50L, 111111L);
            GalleryFile galleryFile10 = createGalleryFile("test", "test", "test", filePath6, user1, -60L, 111111L);
            GalleryFile galleryFile11 = createGalleryFile("test", "test", "test", filePath4, user1, -70L, 111111L);

            em.persist(galleryFile1);
            em.persist(galleryFile2);
            em.persist(galleryFile3);
            em.persist(galleryFile4);
            em.persist(galleryFile5);
            em.persist(galleryFile6);
            em.persist(galleryFile7);
            em.persist(galleryFile8);
            em.persist(galleryFile9);
            em.persist(galleryFile10);
            em.persist(galleryFile11);
        }

        public void categoryInit() {
            Category category1 = createCategory("반팔 티셔츠", "1-1");
            Category category2 = createCategory("긴팔 티셔츠", "1-2");
            Category category3 = createCategory("셔츠", "1-3");
            Category category4 = createCategory("코튼 팬츠", "2-1");
            Category category5 = createCategory("데님 팬츠", "2-2");
            Category category6 = createCategory("레깅스", "2-3");
            Category category7 = createCategory("운동화", "3-1");
            Category category8 = createCategory("구두", "3-2");
            Category category9 = createCategory("샌들", "3-3");
            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
            em.persist(category4);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);
            em.persist(category9);

            CommonItem item1 = createItem("1-1", "상품설명~~~", "반팔티1", 10000, 100, "MEN", category1);
            CommonItem item2 = createItem("1-2", "상품설명~~~", "긴팔티1", 20000, 100, "MEN", category2);
            CommonItem item3 = createItem("1-3", "상품설명~~~", "셔츠1", 32000, 100, "MEN", category3);
            CommonItem item4 = createItem("2-1", "상품설명~~~", "코튼팬츠1", 40000, 100, "WOMEN", category4);
            CommonItem item5 = createItem("2-2", "상품설명~~~", "데님팬츠1", 50000, 100, "WOMEN", category5);
            CommonItem item6 = createItem("2-3", "상품설명~~~", "레깅스1", 60000, 100, "PUBLIC", category6);
            CommonItem item7 = createItem("3-1", "상품설명~~~", "운동화1", 70000, 100, "PUBLIC", category7);
            CommonItem item8 = createItem("3-2", "상품설명~~~", "구두1", 80000, 100, "PUBLIC", category8);
            CommonItem item9 = createItem("3-3", "상품설명~~~", "샌들1", 55000, 100, "PUBLIC", category9);
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);
            em.persist(item6);
            em.persist(item7);
            em.persist(item8);
            em.persist(item9);

            String filePath1 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-blank-1886001_1920.png";
            String filePath2 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-isolated-t-shirt-1852114_1920.png";
            String filePath3 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-nimble-made-7RIMS-NMsbc-unsplash.jpg";
            String filePath4 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-pants-3737413_1920.jpg";
            String filePath5 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-pants-3737415_1920.jpg";
            String filePath6 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-young-female-jogger-waiting-at-the-edge-of-a-zebra-crossing-picjumbo-com.jpg";
            String filePath7 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-revolt-164_6wVEHfI-unsplash.jpg";
            String filePath8 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-nordwood-themes-6V-vJpPzvh8-unsplash.jpg";
            String filePath9 = "https://cqrebucket.s3.ap-northeast-2.amazonaws.com/shopImages/temp-jakob-owens-WzncgWs3RJ4-unsplash.jpg";

            em.persist(createItemImage(item1, -10L, "test", filePath1, "test"));
            em.persist(createItemImage(item1, -10L, "test", filePath2, "test"));
            em.persist(createItemImage(item2, -20L, "test", filePath2, "test"));
            em.persist(createItemImage(item2, -20L, "test", filePath3, "test"));
            em.persist(createItemImage(item3, -30L, "test", filePath3, "test"));
            em.persist(createItemImage(item4, -40L, "test", filePath4, "test"));
            em.persist(createItemImage(item5, -50L, "test", filePath5, "test"));
            em.persist(createItemImage(item6, -60L, "test", filePath6, "test"));
            em.persist(createItemImage(item7, -70L, "test", filePath7, "test"));
            em.persist(createItemImage(item8, -80L, "test", filePath8, "test"));
            em.persist(createItemImage(item9, -90L, "test", filePath9, "test"));
        }


        private Post createPost(String title, String content, int postVies, int recommendation, User user, Board board) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .postViews(postVies)
                    .recommendation(recommendation)
                    .user(user)
                    .board(board)
                    .build();
        }

        private GalleryFile createGalleryFile(String title, String originFilename, String filename, String filepath, User user, Long bundleId, Long bundleOrder) {
            return GalleryFile.builder()
                    .title(title)
                    .filename(filename)
                    .filePath(filepath)
                    .originFilename(originFilename)
                    .user(user)
                    .bundleId(bundleId)
                    .bundleOrder(bundleOrder)
                    .build();
        }


        private Category createCategory(String name, String identificationCode) {
            return Category.builder()
                    .name(name)
                    .identificationCode(identificationCode)
                    .build();
        }

        private CommonItem createItem(String categorySelect, String itemExplanation, String name, int price, int stockCount, String gender, Category category) {
            CreateItemDto dto = new CreateItemDto(name, itemExplanation, price, stockCount, categorySelect, gender);
            CommonItem commonItem = new CommonItem();
            return commonItem.createCommonItem(dto, category);
        }

        private ItemImage createItemImage(Item item, Long bundleId, String filename, String filePath, String originFilename) {
            return ItemImage.builder()
                    .item(item)
                    .bundleId(bundleId)
                    .bundleOrder(System.currentTimeMillis())
                    .filename(filename)
                    .filePath(filePath)
                    .originFilename(originFilename)
                    .build();
        }

        private Coupon createCoupon(int count, Long discountRate, String name, User user) {
            return Coupon.builder()
                    .totalCount(count)
                    .remainCount(count)
                    .discountRate(discountRate)
                    .name(name)
                    .user(user)
                    .build();
        }

    }
}