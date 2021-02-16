package com.cqre.cqre.controller;

import com.cqre.cqre.dto.comment.CreateCommentDto;
import com.cqre.cqre.dto.comment.ReadCommentDto;
import com.cqre.cqre.dto.comment.RemoveCommentDto;
import com.cqre.cqre.dto.post.CreatePostDto;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.ReadPostDto;
import com.cqre.cqre.entity.post.PostFile;
import com.cqre.cqre.repository.CommentRepository;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.service.CommentService;
import com.cqre.cqre.service.PostFileService;
import com.cqre.cqre.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostFileService postFileService;
    private final PostFileRepository postFileRepository;
    private final CommentService commentService;

    /*자유게시판 페이지*/
    @GetMapping("/board/freeBoard")
    public String freeBoard(Model model, @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<ListPostDto> posts = postService.findFreePosts(pageable);
        model.addAttribute("posts", posts);

        return "/board/freeBoard";
    }

    /*공지사항 게시판 페이지*/
    @GetMapping("/board/noticeBoard")
    public String noticeBoard(Model model, @PageableDefault(size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

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
    public String PCreateFreePost(@ModelAttribute("createPostDto") @Valid CreatePostDto createPostDto, BindingResult result,
                                  @RequestParam(value = "file", required = false) List<MultipartFile> files){
        if (result.hasErrors()) {
            return "/post/createFreePost";
        }

        Long postId = postService.createFreePost(createPostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.saveFile(files, postId);
        }

        return "redirect:/board/freeBoard";
    }

    /*공지사항 글 생성*/
    @PostMapping("/post/createNoticePost")
    public String PCreateNoticePost(@ModelAttribute("createPostDto") @Valid CreatePostDto createPostDto, BindingResult result,
                                    @RequestParam(value = "file", required = false) List<MultipartFile> files){

        if (result.hasErrors()) {
            return "/post/createNoticePost";
        }

        Long postId = postService.createNoticePost(createPostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.saveFile(files, postId);
        }

        return "redirect:/board/noticeBoard";
    }

    /*공지사항 글 읽기 페이지*/
    @GetMapping("/post/readPost/{postId}")
    public String readFreePost(@PathVariable("postId") Long postId, Model model){
        /*글 조회*/
        ReadPostDto readPostDto = postService.readPost(postId);
        model.addAttribute("readPostDto", readPostDto);

        /*파일 조회*/
        List<PostFile> postFiles = postFileRepository.findPostFileByPostId(postId);
        model.addAttribute("postFiles", postFiles);

        /*댓글 조회*/
        List<ReadCommentDto> commentDtos = commentService.readComment(postId);
        model.addAttribute("commentDtos", commentDtos);

        CreateCommentDto createCommentDto = new CreateCommentDto();
        model.addAttribute("createCommentDto", createCommentDto);

        RemoveCommentDto removeCommentDto = new RemoveCommentDto();
        model.addAttribute("removeCommentDto", removeCommentDto);

        return "/post/readPost";
    }

}
