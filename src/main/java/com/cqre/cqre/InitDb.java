package com.cqre.cqre;

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

            Post post1 = Post.builder()
                    .title("1111")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user1)
                    .board(Board.FREE)
                    .build();

            Post post2 = Post.builder()
                    .title("2222")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user1)
                    .board(Board.FREE)
                    .build();

            Post post3 = Post.builder()
                    .title("3333")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user1)
                    .board(Board.FREE)
                    .build();

            Post post4 = Post.builder()
                    .title("4444")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.FREE)
                    .build();

            Post post5 = Post.builder()
                    .title("5555")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.FREE)
                    .build();

            Post post6 = Post.builder()
                    .title("6666")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.FREE)
                    .build();

            Post post7 = Post.builder()
                    .title("7777")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.FREE)
                    .build();

            Post post8 = Post.builder()
                    .title("1111")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.NOTICE)
                    .build();

            Post post9 = Post.builder()
                    .title("2222")
                    .content("ssadasd")
                    .postViews(0)
                    .recommendation(0)
                    .user(user2)
                    .board(Board.NOTICE)
                    .build();

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




    }
}
