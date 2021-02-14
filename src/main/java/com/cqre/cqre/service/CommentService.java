package com.cqre.cqre.service;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.ReadCommentDto;
import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.CommentRepository;
import com.cqre.cqre.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public LocalDateTime createComment(CreateCommentDto dto) {
        Post findPost = postRepository.findById(dto.getPostId()).orElseThrow(CPostNotFoundException::new);

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(userService.getLoginUser())
                .post(findPost)
                .depth(1)
                .build();

        Comment saveComment = commentRepository.save(comment);
        return saveComment.getLastModifiedDate();
    }

    /*댓글 조회*/
    public List<ReadCommentDto> readComment(Long postId){
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        return comments.stream()
                .map(c -> new ReadCommentDto(c))
                .collect(Collectors.toList());
    }

}
