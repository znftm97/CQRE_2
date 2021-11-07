package com.cqre.cqre.controller;

import com.cqre.cqre.domain.board.Board;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.PostSearchCondition;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final PostService postService;
    private final PostRepository postRepository;

    /*자유게시판 페이지*/
    @GetMapping("/boards/free-board")
    public String freeBoardPage(@RequestParam(value = "sortSelect", required = false) String sortOption,
                            @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){

        Page<ListPostDto> posts = postService.findFreePosts(sortOption, page);
        model.addAttribute("posts", posts);

        return "/board/freeBoard";
    }

    /*공지사항 게시판 페이지*/
    @GetMapping("/boards/notice-board")
    public String noticeBoardPage(@RequestParam(value = "sortSelect", required = false) String sortOption,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){

        Page<ListPostDto> posts = postService.findNoticePosts(sortOption, page);
        model.addAttribute("posts", posts);

        return "/board/noticeBoard";
    }

    /*자유게시판 검색*/
    @GetMapping("/boards/free-board/search")
    public String searchFreeBoard(Model model, PostSearchCondition condition,
                                  @PageableDefault(size = 6, sort = "id") Pageable pageable){

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
    @GetMapping("/boards/notice-board/search")
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
}
