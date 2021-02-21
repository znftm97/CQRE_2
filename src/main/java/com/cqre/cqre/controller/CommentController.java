package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.RemoveCommentDto;
import com.cqre.cqre.dto.comment.ResponseCommentDto;
import com.cqre.cqre.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*댓글 조회*/
    @GetMapping("/comment/{postId}")
    @ResponseBody
    public List<ResponseCommentDto> readComment(@PathVariable("postId") Long postId){
        return commentService.readComment(postId);
    }

    /*댓글 생성*/
    @PostMapping(value = "/comment/create")
    @ResponseBody
    public void createComment(@RequestBody CreateCommentDto createCommentDto){
        commentService.createComment(createCommentDto);
    }

    /*댓글 삭제*/
    @DeleteMapping(value = "/comment/delete")
    @ResponseBody
    public void deleteComment(@RequestBody RemoveCommentDto removeCommentDto){
        commentService.deleteComment(removeCommentDto);
    }

}
