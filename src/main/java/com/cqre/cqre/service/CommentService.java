package com.cqre.cqre.service;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.ReadCommentDto;
import com.cqre.cqre.dto.comment.ResponseCommentDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.CommentRepository;
import com.cqre.cqre.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    /*댓글 생성*/
    @Transactional
    public List<ResponseCommentDto> createComment(CreateCommentDto dto) {
        Post findPost = postRepository.findById(dto.getPostId()).orElseThrow(CPostNotFoundException::new);
        User loginUser = userService.getLoginUser();

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(loginUser)
                .post(findPost)
                .depth(1)
                .build();

        commentRepository.save(comment);

        List<Comment> commentList = commentRepository.findCommentByPostId(dto.getPostId());
        return commentList.stream()
                            .map(c -> new ResponseCommentDto(c, loginUser))
                            .collect(Collectors.toList());
    }

    /*댓글 조회*/
    public List<ResponseCommentDto> readComment(Long postId){
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        User loginUser = userService.getLoginUser();
        return comments.stream()
                .map(c -> new ResponseCommentDto(c, loginUser))
                .collect(Collectors.toList());
    }

    /*댓글 삭제*/
    @Transactional
    public void deleteComment(Long commentId){
        Comment findComment = commentRepository.findById(commentId).get();
        commentRepository.delete(findComment);
    }
}
