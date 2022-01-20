package com.cqre.cqre.post;

import com.cqre.cqre.domain.board.Board;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.board.PostFile;
import com.cqre.cqre.domain.user.Address;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.post.CreateAndUpdatePostDto;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostFileDto;
import com.cqre.cqre.dto.post.ReadPostDto;
import com.cqre.cqre.exception.customexception.user.CAnonymousUserException;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import com.cqre.cqre.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    UserService userService;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    private User user;
    private User admin;
    private List<Post> posts;

    @BeforeEach
    void beforeEach(){
        user = User.builder()
                    .id(1L)
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
                    .id(2L)
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

        posts = new ArrayList<>();
        posts.add(Post.of("title1", "content1", user, Board.FREE));
        posts.add(Post.of("title2", "content2", user, Board.FREE));
        posts.add(Post.of("title3", "content3", user, Board.FREE));
        posts.add(Post.of("title4", "content4", user, Board.FREE));
        posts.add(Post.of("title5", "content5", user, Board.FREE));
        posts.add(Post.of("title6", "content6", admin, Board.FREE));
    }

    @Test
    @DisplayName("[글 목록 조회] - 자유게시판 글 목록을 조회할 수 있다.")
    public void findFreePosts(){
        //given
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
        // given
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));
        when(postRepository.findPostByBoard(Board.FREE, pageRequest)).thenReturn(new PageImpl<>(posts, pageRequest, posts.size()));

        List<ListPostDto> postDtos = posts.stream()
                .map(ListPostDto::new)
                .collect(Collectors.toList());
        Page<ListPostDto> expected = new PageImpl<>(postDtos, pageRequest, postDtos.size());

        // when
        Page<ListPostDto> findPosts = postService.findFreePosts(null, 0);

        // then
        assertThat(findPosts.getContent()).isEqualTo(expected.getContent());
        assertThat(findPosts.getTotalElements()).isEqualTo(expected.getTotalElements());
        assertThat(findPosts.getTotalPages()).isEqualTo(expected.getTotalPages());
        assertThat(findPosts.getSize()).isEqualTo(expected.getSize());
        assertThat(findPosts.getSort()).isEqualTo(expected.getSort());
    }

    @Test
    @DisplayName("[글 생성] - 로그인한 유저는 글을 생성할 수 있다.")
    public void createFreePost(){
        //given
        CreateAndUpdatePostDto dto = new CreateAndUpdatePostDto("title", "content");
        Post post = Post.of(dto.getTitle(), dto.getContent(), user, Board.FREE);

        when(userService.getLoginUser()).thenReturn(user);

        //when
        Long findPostId = postService.createFreePost(dto);

        //then
        assertThat(findPostId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("[글 생성] - 로그인하지 않은 유저는 글을 생성할 수 없다.")
    public void failureCreateFreePost(){
        //given
        CreateAndUpdatePostDto dto = new CreateAndUpdatePostDto("title", "content");
        when(userService.getLoginUser()).thenThrow(CAnonymousUserException.class);

        //when && then
        assertThatExceptionOfType(CAnonymousUserException.class)
                .isThrownBy(() -> postService.createFreePost(dto));
    }

    @Test
    @DisplayName("[글 생성] - 일반 권한 유저는 공지사항 글을 생성할 수 없다.")
    @Disabled("스프링 시큐리티 필터에서 url에 따라 필터링 되는거라 테스트하기 힘들다.. 일단 보류")
    public void failureCreateNoticePostByUser(){

    }

    @Test
    @DisplayName("[글 조회] - 로그인한 유저는 글을 조회할 수 있다. && 자신이 쓴 글이면 수정 삭제 버튼이 노출된다.(isAuthorCheck == true)")
    public void readMyPost(){
        //given
        Long postId = 1L;
        Post post = posts.get(0);

        when(userService.getLoginUser()).thenReturn(user);
        when(postRepository.CFindByPostId(postId)).thenReturn(Optional.of(post));

        //when
        ReadPostDto dto = postService.readPost(postId);

        //then
        assertThat(dto.getTitle()).isEqualTo(post.getTitle());
        assertThat(dto.getContent()).isEqualTo(post.getContent());
        assertThat(dto.getUserName()).isEqualTo(user.getName());
        assertThat(dto.isAuthorCheck()).isEqualTo(true);
    }

    @Test
    @DisplayName("[글 조회] - 로그인한 유저는 글을 조회할 수 있다. && 자신이 쓴 글이 아니면 수정 삭제 버튼이 노출되지 않는다.(isAuthorCheck == false)")
    public void readNonMyPost(){
        //given
        Long postId = 1L;
        Post post = posts.get(0);

        when(userService.getLoginUser()).thenReturn(admin);
        when(postRepository.CFindByPostId(postId)).thenReturn(Optional.of(post));

        //when
        ReadPostDto dto = postService.readPost(postId);

        //then
        assertThat(dto.getTitle()).isEqualTo(post.getTitle());
        assertThat(dto.getContent()).isEqualTo(post.getContent());
        assertThat(dto.getUserName()).isNotEqualTo(admin.getName());
        assertThat(dto.isAuthorCheck()).isEqualTo(false);
    }

    @Test
    @DisplayName("[글 조회] - 로그인하지 않은 유저는 글을 조회할 수 없다.")
    public void failureReadPost(){
        //given
        Long postId = 1L;
        Post post = posts.get(0);

        when(userService.getLoginUser()).thenThrow(CAnonymousUserException.class);
        when(postRepository.CFindByPostId(postId)).thenReturn(Optional.of(post));

        //when
        assertThatExceptionOfType(CAnonymousUserException.class)
                .isThrownBy(() -> postService.readPost(postId));
    }

    @Test
    @DisplayName("[글 수정] - 글을 수정할 수 있다.")
    public void updatePost(){
        // given
        Post post = posts.get(0);
        Long postId = 1L;
        PostFile postFile = PostFile.of("originFilename", "filename", "filePath", post);
        PostFileDto postFileDto = new PostFileDto(postFile);
        CreateAndUpdatePostDto updatePostDto = new CreateAndUpdatePostDto("updateTitle", "updateContent",
                                                                            postId, Board.FREE, List.of(postFileDto));

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        Post findPost = postService.updatePost(updatePostDto);

        // then
        assertThat(findPost.getTitle()).isEqualTo(updatePostDto.getTitle());
        assertThat(findPost.getContent()).isEqualTo(updatePostDto.getContent());
    }

    @Test
    @DisplayName("[나의 글 조회] - 현재 로그인한 유저가 쓴 글을 조회할 수 있다.")
    public void readMyPostList(){
        // given
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));

        List<ListPostDto> postDtos = posts.stream()
                .map(ListPostDto::new)
                .collect(Collectors.toList());
        Page<ListPostDto> expected = new PageImpl<>(postDtos, pageRequest, postDtos.size());

        when(postRepository.findPostByUserId(user.getId(), pageRequest)).thenReturn(new PageImpl<>(posts, pageRequest, posts.size()));
        when(userService.getLoginUser()).thenReturn(user);

        // when
        Page<ListPostDto> findPosts = postService.myPost(pageRequest);

        // then
        assertThat(findPosts.getContent()).isEqualTo(expected.getContent());
        assertThat(findPosts.getTotalElements()).isEqualTo(expected.getTotalElements());
        assertThat(findPosts.getTotalPages()).isEqualTo(expected.getTotalPages());
        assertThat(findPosts.getSize()).isEqualTo(expected.getSize());
        assertThat(findPosts.getSort()).isEqualTo(expected.getSort());
    }

}
