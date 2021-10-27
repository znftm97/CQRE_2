package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.*;
import com.cqre.cqre.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    /*댓글 조회*/
    @GetMapping("/comments/{postId}")
    @ResponseBody
    public List<ResponseCommentDto> readComment(@PathVariable("postId") Long postId){
        return commentService.readComment(postId);
    }

    /*댓글 생성*/
    @PostMapping(value = "/comments")
    @ResponseBody
    public void createComment(@RequestBody CreateCommentDto createCommentDto){
        commentService.createComment(createCommentDto);
    }

    /*댓글 삭제*/
    @DeleteMapping(value = "/comments")
    @ResponseBody
    public void deleteComment(@RequestBody RemoveCommentDto removeCommentDto){
        commentService.deleteComment(removeCommentDto);
    }

    /*댓글 수정*/
    @PatchMapping("/comments")
    @ResponseBody
    public void updateComment(@RequestBody UpdateCommentDto updateCommentDto){
        commentService.updateComment(updateCommentDto);
    }

    /*대댓글 생성*/
    @PostMapping("/re-comments")
    @ResponseBody
    public void createReComment(@RequestBody CreateReCommentDto createReCommentDto){
        commentService.createReComment(createReCommentDto);
    }

    /*나의 댓글 목록 조회*/
    @GetMapping("/comments/my-info")
    public String commentList(Model model, @PageableDefault(size = 5, sort = "id") Pageable pageable){
        Page<MyCommentDto> myCommentDtos = commentService.myComments(pageable);
        model.addAttribute("commentList", myCommentDtos);
        return "/comment/commentList";
    }
}
