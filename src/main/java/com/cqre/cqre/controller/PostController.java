package com.cqre.cqre.controller;

import com.cqre.cqre.dto.post.CreatePostDto;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostDto;
import com.cqre.cqre.dto.post.ReadPostDto;
import com.cqre.cqre.service.PostService;
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
public class PostController {

    private final PostService postService;

    /*자유게시판 페이지*/
    @GetMapping("/board/freeBoard")
    public String freeBoard(Model model, @PageableDefault(size = 6, sort = "id") Pageable pageable){

        Page<ListPostDto> posts = postService.findFreePosts(pageable);
        model.addAttribute("posts", posts);

        return "/board/freeBoard";
    }

    /*공지사항 게시판 페이지*/
    @GetMapping("/board/noticeBoard")
    public String noticeBoard(Model model, @PageableDefault(size = 6, sort = "id") Pageable pageable){

        Page<ListPostDto> posts = postService.findNoticePosts(pageable);
        model.addAttribute("posts", posts);

        return "/board/noticeBoard";
    }

    /*자유게시판 글 생성 페이지*/
    @GetMapping("/post/createFreePost")
    public String createFreePost(Model model){

        model.addAttribute("createPostDto", new CreatePostDto());

        return "/post/createFreePost";
    }

    /*공지사항 글 생성 페이지*/
    @GetMapping("/post/createNoticePost")
    public String createNoticePost(Model model){

        model.addAttribute("createPostDto", new CreatePostDto());

        return "/post/createNoticePost";
    }

    /*자유게시판 글 생성*/
    @PostMapping("/post/createFreePost")
    public String PCreateFreePost(@ModelAttribute("createPostDto") CreatePostDto createPostDto){
        postService.createFreePost(createPostDto);

        return "redirect:/board/freeBoard";
    }

    /*공지사항 글 생성*/
    @PostMapping("/post/createNoticePost")
    public String PCreateNoticePost(@ModelAttribute("createPostDto") CreatePostDto createPostDto){
        postService.createNoticePost(createPostDto);

        return "redirect:/board/noticeBoard";
    }

    /*자유게시판,공지사항 글 읽기 페이지*/
    @GetMapping("/post/readPost/{postId}")
    public String readFreePost(@PathVariable("postId") Long postId, Model model){
        ReadPostDto readPostDto = postService.readPost(postId);
        model.addAttribute("readPostDto", readPostDto);
        return "/post/readPost";
    }

}
