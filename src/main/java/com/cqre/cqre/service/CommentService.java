package com.cqre.cqre.service;

import com.cqre.cqre.domain.board.Comment;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.comment.*;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.comment.CommentRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private static final int RE_COMMENT_DEPTH = 2;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final AtomicLong bundleId = new AtomicLong(1);

    /*댓글 생성*/
    @Transactional
    public void createComment(CreateCommentDto createCommentDto) {
        Post findPost = postRepository.findById(createCommentDto.getPostId()).orElseThrow(CPostNotFoundException::new);
        User loginUser = userService.getLoginUser();

        commentRepository.save(createCommentDto.toEntity(findPost, loginUser, bundleId));
    }

    /*댓글 조회*/
    public List<ResponseCommentDto> readComment(Long postId){
        User loginUser = userService.getLoginUser();

        List<Comment> commentList = commentRepository.findCommentAllByPost(postId);
        return commentList.stream()
                .map(c -> new ResponseCommentDto(c, loginUser))
                .collect(Collectors.toList());
    }


    /*댓글 삭제*/
    @Transactional
    public void deleteComment(RemoveCommentDto removeCommentDto){
        Comment findComment = commentRepository.findById(removeCommentDto.getCommentId()).get();
        findComment.removeComment();
    }

    /*댓글 수정*/
    @Transactional
    public void updateComment(UpdateCommentDto updateCommentDto){
        Comment findComment = commentRepository.findById(updateCommentDto.getCommentId()).get();
        findComment.updateComment(updateCommentDto.getUpdateContent());
    }

    /*대댓글 생성*/
    @Transactional
    public void createReComment(CreateReCommentDto createReCommentDto){
        Post findPost = postRepository.findById(createReCommentDto.getPostId()).orElseThrow(CPostNotFoundException::new);
        User loginUser = userService.getLoginUser();
        Comment findComment = commentRepository.findById(createReCommentDto.getOriginalCommentId()).get();

        Comment comment = Comment.builder()
                .content(createReCommentDto.getContent())
                .user(loginUser)
                .post(findPost)
                .depth(RE_COMMENT_DEPTH)
                .bundleId(findComment.getBundleId())
                .bundleOrder(System.currentTimeMillis())
                .existsCheck(true)
                .build();

        commentRepository.save(comment);
    }

    /*내 댓글 조회*/
    public Page<MyCommentDto> myComments(Pageable pageable){
        Page<Comment> findComments = commentRepository.findCommentByUserId(userService.getLoginUser().getId(), true, pageable);
        return findComments.map(MyCommentDto::new);
    }
}
