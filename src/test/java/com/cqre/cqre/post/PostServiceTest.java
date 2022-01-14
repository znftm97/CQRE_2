package com.cqre.cqre.post;

import com.cqre.cqre.domain.board.Board;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.user.Address;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    private User user;
    private User admin;

    @BeforeEach
    void beforeEach(){
        user = User.builder()
                    .name("user")
                    .studentId("11111111")
                    .loginId("loginId")
                    .password("password")
                    .email("email@email.com")
                    .address(new Address("street", "detail"))
                    .emailVerified("true")
                    .emailCheckToken(UUID.randomUUID().toString())
                    .role("USER")
                    .build();

        admin = User.builder()
                    .name("admin")
                    .studentId("99999999")
                    .loginId("loginId")
                    .password("password")
                    .email("email@email.com")
                    .address(new Address("street", "detail"))
                    .emailVerified("true")
                    .emailCheckToken(UUID.randomUUID().toString())
                    .role("ADMIN")
                    .build();
    }

    @Test
    @DisplayName("[글 목록 조회] - 자유게시판 글 목록을 조회할 수 있다.")
    public void findFreePosts(){

        // given
        List<Post> posts = new ArrayList<>();
        posts.add(Post.of("title1", "content1", user, Board.FREE));
        posts.add(Post.of("title2", "content2", user, Board.FREE));
        posts.add(Post.of("title3", "content3", user, Board.FREE));
        posts.add(Post.of("title4", "content3", user, Board.FREE));
        posts.add(Post.of("title5", "content3", user, Board.FREE));
        posts.add(Post.of("title6", "content3", user, Board.FREE));

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));
        when(postRepository.findPostByBoard(Board.FREE, pageRequest)).thenReturn(new PageImpl<>(posts, pageRequest, posts.size()));

        List<ListPostDto> postDtos = posts.stream()
                                            .map(ListPostDto::new)
                                            .collect(Collectors.toList());
        Page<ListPostDto> expected = new PageImpl<>(postDtos, pageRequest, postDtos.size());

        // when
        Page<ListPostDto> findPosts = postService.findFreePosts("id", 0);

        // then
        assertThat(findPosts.getContent()).isEqualTo(expected.getContent());
        assertThat(findPosts.getTotalElements()).isEqualTo(expected.getTotalElements());
        assertThat(findPosts.getTotalPages()).isEqualTo(expected.getTotalPages());
        assertThat(findPosts.getSize()).isEqualTo(expected.getSize());
        assertThat(findPosts.getSort()).isEqualTo(expected.getSort());
    }

    @Test
    @DisplayName("[글 목록 조회] - sortOption이 null이여도 목록을 조회할 수 있다.")
    public void findFreePostsSortOptionIsNull(){

    }

    @Test
    @DisplayName("[글 목록 조회] - admin 권한 유저는 공지사항 글 목록을 조회할 수 있다.")
    public void findNoticePosts(){

    }

    @Test
    @DisplayName("[글 목록 조회] - 일반 권한 유저는 공지사항 글 목록을 조회할 수 없다.")
    public void failureFindNoticePosts(){

    }

    @Test
    @DisplayName("[글 생성] - 로그인한 유저는 글을 생성할 수 있다.")
    public void createFreePost(){

    }

    @Test
    @DisplayName("[글 생성] - 로그인하지 않은 유저는 글을 생성할 수 없다.")
    public void failureCreateFreePost(){

    }

    @Test
    @DisplayName("[글 생성] - 일반 권한 유저는 공지사항 글을 생성할 수 없다.")
    public void failureCreateNoticePostByUser(){

    }

    @Test
    @DisplayName("[글 조회] - 로그인한 유저는 글을 조회할 수 있다.")
    public void readPost(){

    }

    @Test
    @DisplayName("[글 조회] - 로그인하지 않은 유저는 글을 조회할 수 없다.")
    public void failureReadPost(){

    }

    @Test
    @DisplayName("[글 삭제] - 글을 삭제할 수 있다.")
    public void removePost(){

    }

    @Test
    @DisplayName("[글 수정] - 글을 수정할 수 있다.")
    public void updatePost(){

    }

    @Test
    @DisplayName("[나의 글 조회] - 현재 로그인한 유저가 쓴 글을 조회할 수 있다.")
    public void readMyPost(){

    }



}
