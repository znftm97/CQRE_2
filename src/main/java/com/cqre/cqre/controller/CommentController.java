package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.ResponseCommentDto;
import com.cqre.cqre.service.CommentService;
import com.cqre.cqre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    /*댓글 생성*/
    @PostMapping(value = "/comment/create")
    @ResponseBody
    public List<ResponseCommentDto> create(@RequestBody CreateCommentDto createCommentDto){
        return commentService.createComment(createCommentDto);
    }

    /*댓글 삭제*/
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
