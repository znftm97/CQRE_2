package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.RemoveCommentDto;
import com.cqre.cqre.dto.comment.ResponseCommentDto;
import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.service.CommentService;
import com.cqre.cqre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping(value = "/comment/create")
    @ResponseBody
    public ResponseCommentDto create(@RequestBody CreateCommentDto createCommentDto){
        Comment saveComment = commentService.createComment(createCommentDto);

        ResponseCommentDto responseCommentDto = new ResponseCommentDto(saveComment);
        responseCommentDto.setUsername(userService.getLoginUser().getName());

        return responseCommentDto;
    }

    @PostMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        System.out.println("11");
        commentService.deleteComment(commentId);
        return "redirect:/home";
    }

    /*@PostMapping(value = "/comment/delete")
    @ResponseBody
    public int deleteComment(@RequestBody RemoveCommentDto removeCommentDto){
        System.out.println("11");
        commentService.deleteComment(removeCommentDto.getId());
        return 1;
    }*/

}
