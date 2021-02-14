package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.ResponseCommentDto;
import com.cqre.cqre.service.CommentService;
import com.cqre.cqre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping(value = "/comment/create")
    @ResponseBody
    public ResponseCommentDto create(@RequestBody CreateCommentDto createCommentDto){
        LocalDateTime lastModifiedDate = commentService.createComment(createCommentDto);

        ResponseCommentDto responseCommentDto = new ResponseCommentDto(
                userService.getLoginUser().getName(), lastModifiedDate, createCommentDto.getContent());

        return responseCommentDto;
    }
}
