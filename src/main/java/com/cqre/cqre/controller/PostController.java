package com.cqre.cqre.controller;

import com.cqre.cqre.domain.board.Board;
import com.cqre.cqre.domain.board.Post;
import com.cqre.cqre.dto.post.CreateAndUpdatePostDto;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostFileDto;
import com.cqre.cqre.dto.post.ReadPostDto;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostFileService;
import com.cqre.cqre.service.PostService;
import com.cqre.cqre.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostFileService postFileService;
    private final RecommendationService recommendationService;
    private final PostRepository postRepository;

    /*자유게시판 글 생성 페이지*/
    @GetMapping("/posts/free-board/page")
    public String createFreePostPage(Model model){

        model.addAttribute("createAndUpdatePostDto", new CreateAndUpdatePostDto());

        return "/post/createFreePost";
    }

    /*공지사항 글 생성 페이지*/
    @GetMapping("/posts/notice-board/page")
    public String createNoticePostPage(Model model){

        model.addAttribute("createAndUpdatePostDto", new CreateAndUpdatePostDto());

        return "/post/createNoticePost";
    }

    /*자유게시판 글 생성*/
    @PostMapping("/posts/free-board")
    public String createFreePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                                  @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        if (result.hasErrors()) {
            return "/post/createFreePost";
        }

        Long postId = postService.createFreePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload(files, "postFiles", postId);
        }

        return "redirect:/boards/free-board";
    }

    /*공지사항 글 생성*/
    @PostMapping("/posts/notice-board")
    public String createNoticePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                                    @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {

        if (result.hasErrors()) {
            return "/post/createNoticePost";
        }

        Long postId = postService.createNoticePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload(files, "postFiles", postId);
        }

        return "redirect:/boards/notice-board";
    }

    /*글 조회 페이지*/
    @GetMapping("/posts/{postId}")
    public String readFreePost(@PathVariable("postId") Long postId, Model model){
        /*글 조회*/
        ReadPostDto readPostDto = postService.readPost(postId);
        model.addAttribute("readPostDto", readPostDto);

        /*파일 조회*/
        List<PostFileDto> postFileDtos = postFileService.readPostFiles(postId);
        model.addAttribute("postFileDtos", postFileDtos);

        /*추천 조회*/
        String recommendationCheck = recommendationService.recommendationCheck(postId);
        model.addAttribute("recommendationCheck", recommendationCheck);

        return "/post/readPost";
    }

    /*글 삭제*/
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.removePost(postId);
        return "redirect:/boards/free-board";
    }

    /*글 수정 페이지*/
    @GetMapping("/posts/{postId}/update-page")
    public String updatePostPage(@PathVariable("postId") Long postId, Model model) {
        CreateAndUpdatePostDto createAndUpdatePostDto = postService.updatePostPage(postId);
        model.addAttribute("createAndUpdatePostDto", createAndUpdatePostDto);


        if (createAndUpdatePostDto.getBoard() == Board.FREE) {
            return "/post/updatePostFree";
        }else {
            return "/post/updatePostNotice";
        }
    }

    /*글 수정*/
    @PatchMapping("/posts")
    public String updatePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                              @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        if (result.hasErrors() && (createAndUpdatePostDto.getBoard()==Board.FREE) ) {
            return "/post/updatePostFree";
        } else if (result.hasErrors() && (createAndUpdatePostDto.getBoard() == Board.NOTICE)) {
            return "/post/updatePostNotice";
        }

        Post updatePost = postService.updatePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload(files, "postFiles", createAndUpdatePostDto.getId());
        }

        if(updatePost.getBoard() == Board.FREE){
            return "redirect:/boards/free-board";
        }else {
            return "redirect:/boards/notice-board";
        }
    }

    /*내가 쓴 글 조회*/
    @GetMapping("/posts/my-info")
    public String readPostMy(Model model, @PageableDefault(size=5, sort = "id") Pageable pageable){
        Page<ListPostDto> listPostDtos = postService.myPost(pageable);
        model.addAttribute("listPostDtos", listPostDtos);
        return "/post/postList";
    }

}
