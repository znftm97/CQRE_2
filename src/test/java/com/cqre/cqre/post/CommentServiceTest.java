package com.cqre.cqre.post;

import com.cqre.cqre.domain.board.Comment;
import com.cqre.cqre.dto.comment.*;
import com.cqre.cqre.exception.ExceptionStatus;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.comment.CommentRepository;
import com.cqre.cqre.security.WithMockCustomUser;
import com.cqre.cqre.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("글에 댓글을 생성할 수 있다.")
    @WithMockCustomUser
    public void createComment(){
        // given
        Long postId = 9L;
        CreateCommentDto dto = new CreateCommentDto("content", postId);

        // when
        commentService.createComment(dto);

        //then
        List<Comment> commentsByPost = commentRepository.findCommentByPostId(postId);

        assertThat(commentsByPost.size()).isEqualTo(1);
        assertThat(commentsByPost.get(0).getContent()).isEqualTo(dto.getContent());
        assertThat(commentsByPost.get(0).getPost().getId()).isEqualTo(postId);
    }

    @Test
    @DisplayName("존재하지 않는 글에 댓글을 생성할 수 없다.")
    public void failureCreateComment(){
        // given
        CreateCommentDto dto = new CreateCommentDto("content", 999L);

        // when && then
        assertThatExceptionOfType(CPostNotFoundException.class)
                .isThrownBy(() -> commentService.createComment(dto))
                .withMessageMatching(ExceptionStatus.POST_NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("글의 댓글을 조회할 수 있다.")
    @WithMockCustomUser
    public void getComment(){
        // given
        Long postId = 9L;
        CreateCommentDto createCommentDto = new CreateCommentDto("content", postId);
        commentService.createComment(createCommentDto);

        List<Comment> findCommentsByPost = commentRepository.findCommentByPostId(postId);
        Comment findComment = findCommentsByPost.get(0);

        // when
        List<ResponseCommentDto> dtos = commentService.readComment(postId);

        // then
        assertThat(dtos).isNotEmpty();
        assertThat(findComment.getContent()).isEqualTo(createCommentDto.getContent());
        assertThat(findComment.getPost().getId()).isEqualTo(createCommentDto.getPostId());
    }

    @Test
    @DisplayName("글의 댓글을 삭제할 수 있다.")
    @WithMockCustomUser
    public void deleteComment(){
        // given
        Long postId = 9L;
        CreateCommentDto createCommentDto = new CreateCommentDto("content", postId);
        commentService.createComment(createCommentDto);

        List<Comment> findCommentsByPost = commentRepository.findCommentByPostId(postId);
        Comment findComment = findCommentsByPost.get(0);

        //when
        commentService.deleteComment(new RemoveCommentDto(findComment.getId()));

        //then
        assertThat(findComment.isExistsCheck()).isFalse();
    }

    @Test
    @DisplayName("글의 댓글을 수정할 수 있다.")
    @WithMockCustomUser
    public void updateComment(){
        // given
        Long postId = 9L;
        CreateCommentDto createCommentDto = new CreateCommentDto("content", postId);
        commentService.createComment(createCommentDto);

        List<Comment> findCommentsByPost = commentRepository.findCommentByPostId(postId);
        Comment findComment = findCommentsByPost.get(0);

        UpdateCommentDto updateCommentDto = new UpdateCommentDto(findComment.getId(), "update content");

        //when
        commentService.updateComment(updateCommentDto);

        //then
        assertThat(findComment.getId()).isEqualTo(updateCommentDto.getCommentId());
        assertThat(findComment.getContent()).isEqualTo(updateCommentDto.getUpdateContent());
    }

    @Test
    @DisplayName("댓글의 댓글(대댓글)을 생성할 수 있다.")
    @WithMockCustomUser
    public void createReComment(){
        // given
        Long postId = 9L;
        CreateCommentDto createCommentDto = new CreateCommentDto("content", postId);
        commentService.createComment(createCommentDto);

        List<Comment> findCommentsByPost = commentRepository.findCommentByPostId(postId);
        Comment findParentComment = findCommentsByPost.get(0);

        CreateReCommentDto createReCommentDto = new CreateReCommentDto("content", postId, findParentComment.getId());

        // when
        commentService.createReComment(createReCommentDto);

        // then
        List<Comment> afterFindCommentsByPost = commentRepository.findCommentByPostId(postId);
        Comment parentComment = afterFindCommentsByPost.get(0);
        Comment childComment = afterFindCommentsByPost.get(1);

        assertThat(afterFindCommentsByPost.size()).isEqualTo(2);
        assertThat(parentComment.getBundleId()).isEqualTo(childComment.getBundleId());
        assertThat(parentComment.getPost()).isEqualTo(childComment.getPost());
        assertThat(childComment.getDepth()).isEqualTo(2);
    }

    @Test
    @DisplayName("로그인한 사용자가 쓴 && 삭제하지 않은 댓글들을 조회할 수 있다.")
    @WithMockCustomUser
    public void getMyComment(){
        // given
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<MyCommentDto> myCommentDtos = commentService.myComments(pageRequest);

        // then
        assertThat(myCommentDtos).isNotEmpty();
        assertThat(myCommentDtos.getSize()).isEqualTo(pageRequest.getPageSize());
        assertThat(myCommentDtos.getSort()).isEqualTo(pageRequest.getSort());
    }

}
