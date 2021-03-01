package com.cqre.cqre;

import com.cqre.cqre.controller.PostController;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class CqreApplicationTests {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostService postService;
	@Autowired
	private PostController postController;

	@Test
	void contextLoads() {
		Post post1 = Post.builder()
				.content("aaaa")
				.title("aaaa")
				.board(Board.FREE)
				.postViews(1)
				.build();

		Post post2 = Post.builder()
				.content("bbbb")
				.title("bbbb")
				.board(Board.FREE)
				.postViews(2)
				.build();

		User user = User.builder()
				.name("userA")
				.email("asd@naver.com")
				.loginId("userA")
				.build();

		PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "id"));
		Page<Post> posts1 = postRepository.findPostByBoard(Board.FREE, pageRequest);
		Page<Post> posts2 = postRepository.findPostByBoard2(Board.FREE, pageRequest);



	}


}
