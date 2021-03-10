package com.cqre.cqre;

import com.cqre.cqre.entity.GalleryFile;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void init(){
            User user1 = User.builder()
                    .name("이지훈")
                    .studentId("20144339")
                    .loginId("znftm97")
                    .password(passwordEncoder.encode("wprkfrhdaud12!"))
                    .email("znftm97@gmail.com")
                    .build();

            User user2 = User.builder()
                    .name("홍길동")
                    .studentId("12341234")
                    .loginId("123")
                    .password(passwordEncoder.encode("123"))
                    .email("znftm93@gmail.com")
                    .build();

            user1.setEmailVerified("true");
            user2.setEmailVerified("true");
            em.persist(user1);
            em.persist(user2);

            Post post1 = createPost("1111", "1111", 0, 0, user1, Board.FREE);
            Post post2 = createPost("2222", "2222", 0, 0, user1, Board.FREE);
            Post post3 = createPost("3333", "3333", 0, 0, user1, Board.FREE);
            Post post4 = createPost("4444", "4444", 0, 0, user1, Board.FREE);
            Post post5 = createPost("5555", "5555", 0, 0, user2, Board.FREE);
            Post post6 = createPost("6666", "6666", 0, 0, user2, Board.FREE);
            Post post7 = createPost("7777", "7777", 0, 0, user2, Board.FREE);
            Post post8 = createPost("1111", "1111", 0, 0, user2, Board.NOTICE);
            Post post9 = createPost("2222", "2222", 0, 0, user2, Board.NOTICE);


            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
            em.persist(post6);
            em.persist(post7);
            em.persist(post8);
            em.persist(post9);
        }

        private Post createPost(String title, String content, int postVies, int recommendation, User user, Board board){
            return Post.builder()
                    .title(title)
                    .content(content)
                    .postViews(postVies)
                    .recommendation(recommendation)
                    .user(user)
                    .board(board)
                    .build();
        }

        private GalleryFile createGalleryFile(String title, String originFilename, Long bundleId, Long bundleOrder) {
            return GalleryFile.builder()
                    .title(title)
                    .originFilename(originFilename)
                    .bundleId(bundleId)
                    .bundleOrder(bundleOrder)
                    .build();
        }



    }
}
