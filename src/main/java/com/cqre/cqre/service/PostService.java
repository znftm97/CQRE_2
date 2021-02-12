package com.cqre.cqre.service;

import com.cqre.cqre.dto.post.CreatePostDto;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.dto.post.ReadPostDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    /*자유게시판 글 목록 출력*/
    public Page<ListPostDto> findFreePosts(Pageable pageable){
        return postRepository.findByPostAll(Board.FREE, pageable).map(post -> new ListPostDto(post));
    }

    /*공지사항 글 목록 출력*/
    public Page<ListPostDto> findNoticePosts(Pageable pageable){
        return postRepository.findByPostAll(Board.NOTICE, pageable).map(post -> new ListPostDto(post));
    }

    /*자유게시판 글 생성*/
    @Transactional
    public void createFreePost(CreatePostDto createPostDto){
        User loginUser = userService.getLoginUser();
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .postViews(0)
                .recommendation(0)
                .user(loginUser)
                .board(Board.FREE)
                .build();

        postRepository.save(post);
    }

    /*공지사항 글 생성*/
    @Transactional
    public void createNoticePost(CreatePostDto createPostDto){
        User loginUser = userService.getLoginUser();
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .postViews(0)
                .recommendation(0)
                .user(loginUser)
                .board(Board.NOTICE)
                .build();

        postRepository.save(post);
    }

    /*글 읽기*/
    @Transactional
    public ReadPostDto readPost(Long postId){

        Post findPost = postRepository.CFindByPostId(postId).orElseThrow(CPostNotFoundException::new);
        findPost.addPostViews();

        ReadPostDto dto = modelMapper.map(findPost, ReadPostDto.class);
        dto.setUserName(findPost.getUser().getName());

        return dto;
    }
}
