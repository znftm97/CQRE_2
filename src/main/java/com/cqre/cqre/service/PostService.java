package com.cqre.cqre.service;

import com.cqre.cqre.dto.post.*;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.domain.board.*;
import com.cqre.cqre.exception.customexception.post.CPostNotFoundException;
import com.cqre.cqre.repository.PostFileRepository;
import com.cqre.cqre.repository.comment.CommentRepository;
import com.cqre.cqre.repository.RecommendationRepository;
import com.cqre.cqre.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostFileRepository postFileRepository;
    private final RecommendationRepository recommendationRepository;
    private final ModelMapper modelMapper;

    /*자유게시판 글 목록 출력*/
    public Page<ListPostDto> findFreePosts(String sortOption, int page){
        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "id"));

        if(sortOption == null){
        }
        else if(sortOption.equals("recommendation")){
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sortOption));
        }
        else if (sortOption.equals("postViews")) {
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sortOption));
        }

        return postRepository.findPostByBoard(Board.FREE, pageRequest).map(post -> new ListPostDto(post));
    }

    /*공지사항 글 목록 출력*/
    public Page<ListPostDto> findNoticePosts(String sortOption, int page){
        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "id"));

        if(sortOption == null){
        }
        else if(sortOption.equals("recommendation")){
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sortOption));
        }
        else if (sortOption.equals("postViews")) {
            pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sortOption));
        }

        return postRepository.findPostByBoard(Board.NOTICE, pageRequest).map(post -> new ListPostDto(post));
    }

    /*자유게시판 글 생성*/
    @Transactional
    public Long createFreePost(CreateAndUpdatePostDto createAndUpdatePostDto){
        User loginUser = userService.getLoginUser();
        /*Post post = Post.builder()
                .title(createAndUpdatePostDto.getTitle())
                .content(createAndUpdatePostDto.getContent())
                .postViews(0)
                .recommendation(0)
                .user(loginUser)
                .board(Board.FREE)
                .build();*/

        Post post = Post.createPost(createAndUpdatePostDto.getTitle(), createAndUpdatePostDto.getContent(),
                                0, 0, loginUser, Board.FREE);

        postRepository.save(post);

        return post.getId();
    }

    /*공지사항 글 생성*/
    @Transactional
    public Long createNoticePost(CreateAndUpdatePostDto createAndUpdatePostDto){
        User loginUser = userService.getLoginUser();
        Post post = Post.builder()
                .title(createAndUpdatePostDto.getTitle())
                .content(createAndUpdatePostDto.getContent())
                .postViews(0)
                .recommendation(0)
                .user(loginUser)
                .board(Board.NOTICE)
                .build();

        postRepository.save(post);

        return post.getId();
    }

    /*글 읽기*/
    @Transactional
    public ReadPostDto readPost(Long postId){

        Post findPost = postRepository.CFindByPostId(postId).orElseThrow(CPostNotFoundException::new);
        findPost.addPostViews();

        ReadPostDto dto = modelMapper.map(findPost, ReadPostDto.class);
        dto.setUserName(findPost.getUser().getName());

        if (findPost.getUser().getId().equals(userService.getLoginUser().getId())) {
            dto.setAuthorCheck(true);
        }

        return dto;
    }

    /*글 읽기*/
    /*@Transactional
    public ReadPostWithDto readPost2(Long postId) {
        postRepository.readPost(postId);
    }*/

    /*글 삭제*/
    @Transactional
    public void removePost(Long postId){
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        List<Long> commentIds = comments
                .stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());
        commentRepository.deleteAllByIdInQuery(commentIds);

        List<PostFile> postFiles = postFileRepository.findPostFileByPostId(postId);
        List<Long> postFileIds = postFiles
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        postFileRepository.deleteAllByIdInQuery(postFileIds);

        List<Recommendation> recommendations = recommendationRepository.findRecommendationByPostId(postId);
        List<Long> recommendationIds = recommendations
                .stream()
                .map(r -> r.getId())
                .collect(Collectors.toList());
        recommendationRepository.deleteAllByIdInQuery(recommendationIds);

        Post findPost = postRepository.CFindByPostId(postId).orElseThrow(CPostNotFoundException::new);
        postRepository.delete(findPost);
    }

    /*글 수정 페이지*/
    /*글이랑 파일 같이 조회하는 쿼리 작성해서 성능 최적화 해보자*/
    public CreateAndUpdatePostDto updatePostPage(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        List<PostFile> postFiles = postFileRepository.findPostFileByPostId(postId);
        List<PostFileDto> postFileDtos = postFiles.stream()
                .map(p -> new PostFileDto(p))
                .collect(Collectors.toList());

        return new CreateAndUpdatePostDto(findPost.getTitle(), findPost.getContent(), findPost.getId(), findPost.getBoard(), postFileDtos);
    }

    /*글 수정*/
    @Transactional
    public Post updatePost(CreateAndUpdatePostDto createAndUpdatePostDto) {
        Post findPost = postRepository.findById(createAndUpdatePostDto.getId()).orElseThrow(CPostNotFoundException::new);
        findPost.updatePost(createAndUpdatePostDto);

        return findPost;
    }

    /*내 글 목록*/
    public Page<ListPostDto> myPost(Pageable pageable){
        Page<Post> findPosts = postRepository.findPostByUserId(userService.getLoginUser().getId(), pageable);
        return findPosts.map(p -> new ListPostDto(p));
    }
}
