package com.cqre.cqre.controller;

import com.cqre.cqre.dto.post.*;
import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.CommentService;
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
    private final PostFileRepository postFileRepository;
    private final CommentService commentService;
    private final RecommendationService recommendationService;
    private final PostRepository postRepository;

    /*자유게시판 페이지*/
    @GetMapping("/board/freeBoard")
    public String freeBoard(@RequestParam(value = "sortSelect", required = false) String sortOption,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){

        Page<ListPostDto> posts = postService.findFreePosts(sortOption, page);
        model.addAttribute("posts", posts);

        return "/board/freeBoard";
    }

    /*공지사항 게시판 페이지*/
    @GetMapping("/board/noticeBoard")
    public String noticeBoard(@RequestParam(value = "sortSelect", required = false) String sortOption,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){

        Page<ListPostDto> posts = postService.findNoticePosts(sortOption, page);
        model.addAttribute("posts", posts);

        return "/board/noticeBoard";
    }

    /*자유게시판 검색*/
    @GetMapping("/board/freeBoard/search")
    public String searchFreeBoard(Model model, PostSearchCondition condition,
                                  @PageableDefault(size = 6, sort = "id")Pageable pageable){

        if(condition.getSearchSelect().equals("title")){
            condition.setTitle(condition.getSearchWord());
        } else if (condition.getSearchSelect().equals("name")) {
            condition.setName(condition.getSearchWord());
        }
        condition.setBoard(Board.FREE);

        Page<ListPostDto> posts = postRepository.searchPost(condition, pageable);
        model.addAttribute("posts", posts);

        return "/board/freeBoard";
    }

    /*공시사항 검색*/
    @GetMapping("/board/noticeBoard/search")
    public String searchNoticeBoard(Model model, PostSearchCondition condition,
                                  @PageableDefault(size = 6, sort = "id")Pageable pageable){

        if(condition.getSearchSelect().equals("title")){
            condition.setTitle(condition.getSearchWord());
        } else if (condition.getSearchSelect().equals("name")) {
            condition.setName(condition.getSearchWord());
        }
        condition.setBoard(Board.NOTICE);

        Page<ListPostDto> posts = postRepository.searchPost(condition, pageable);
        model.addAttribute("posts", posts);

        return "/board/noticeBoard";
    }

    /*자유게시판 글 생성 페이지*/
    @GetMapping("/post/createFreePost")
    public String createFreePost(Model model){

        model.addAttribute("createAndUpdatePostDto", new CreateAndUpdatePostDto());

        return "/post/createFreePost";
    }

    /*공지사항 글 생성 페이지*/
    @GetMapping("/post/createNoticePost")
    public String createNoticePost(Model model){

        model.addAttribute("createAndUpdatePostDto", new CreateAndUpdatePostDto());

        return "/post/createNoticePost";
    }

    /*자유게시판 글 생성*/
    @PostMapping("/post/createFreePost")
    public String PCreateFreePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                                  @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        if (result.hasErrors()) {
            return "/post/createFreePost";
        }

        Long postId = postService.createFreePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload1(files, "postFiles", postId);
        }

        return "redirect:/board/freeBoard";
    }

    /*공지사항 글 생성*/
    @PostMapping("/post/createNoticePost")
    public String PCreateNoticePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                                    @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {

        if (result.hasErrors()) {
            return "/post/createNoticePost";
        }

        Long postId = postService.createNoticePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload1(files, "postFiles", postId);
        }

        return "redirect:/board/noticeBoard";
    }

    /*글 조회 페이지*/
    @GetMapping("/post/{postId}/readPost")
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
    @PostMapping("/post/remove/{postId}")
    public String removePost(@PathVariable("postId") Long postId){
        postService.removePost(postId);
        return "redirect:/board/freeBoard";
    }

    /*글 수정 페이지*/
    @GetMapping("/post/update/{postId}")
    public String updatePost(@PathVariable("postId") Long postId, Model model) {
        CreateAndUpdatePostDto createAndUpdatePostDto = postService.updatePostPage(postId);
        model.addAttribute("createAndUpdatePostDto", createAndUpdatePostDto);


        if (createAndUpdatePostDto.getBoard() == Board.FREE) {
            return "/post/updatePostFree";
        }else {
            return "/post/updatePostNotice";
        }
    }

    /*글 수정*/
    @PostMapping("/post/update")
    public String PUpdatePost(@ModelAttribute("createAndUpdatePostDto") @Valid CreateAndUpdatePostDto createAndUpdatePostDto, BindingResult result,
                              @RequestParam(value = "file", required = false) List<MultipartFile> files) throws IOException {
        if (result.hasErrors() && (createAndUpdatePostDto.getBoard()==Board.FREE) ) {
            return "/post/updatePostFree";
        } else if (result.hasErrors() && (createAndUpdatePostDto.getBoard() == Board.NOTICE)) {
            return "/post/updatePostNotice";
        }

        Post updatePost = postService.updatePost(createAndUpdatePostDto);
        if (!((files.get(0).getOriginalFilename()).isEmpty())){
            postFileService.upload1(files, "postFiles", createAndUpdatePostDto.getId());
        }

        if(updatePost.getBoard() == Board.FREE){
            return "redirect:/board/freeBoard";
        }else {
            return "redirect:/board/noticeBoard";
        }
    }

    /*내가 쓴 글 조회*/
    @GetMapping("/postList")
    public String postList(Model model, @PageableDefault(size=5, sort = "id") Pageable pageable){
        Page<ListPostDto> listPostDtos = postService.myPost(pageable);
        model.addAttribute("listPostDtos", listPostDtos);
        return "/post/postList";
    }

}
