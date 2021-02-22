package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.*;
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

    /*댓글 수정*/
    @PostMapping("/comment/update")
    @ResponseBody
    public void updateComment(@RequestBody UpdateCommentDto updateCommentDto){
        commentService.updateComment(updateCommentDto);
    }

    /*대댓글 생성*/
    @PostMapping("/reComment/create")
    @ResponseBody
    public void createReComment(@RequestBody CreateReCommentDto createReCommentDto){
        commentService.createReComment(createReCommentDto);
    }
}
