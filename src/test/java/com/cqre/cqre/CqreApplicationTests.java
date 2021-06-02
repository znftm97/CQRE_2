package com.cqre.cqre;

import com.cqre.cqre.controller.PostController;
import com.cqre.cqre.dto.post.ListPostDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.entity.post.Board;
import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.entity.post.Post;
import com.cqre.cqre.entity.post.Recommendation;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import com.cqre.cqre.service.RecommendationService;
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
	@Autowired
	private RecommendationService recommendationService;

	@Test
	void contextLoads() {
		Post post = Post.builder()
				.content("aaaa")
				.title("aaaa")
				.board(Board.FREE)
				.postViews(1)
				.build();

		User user = User.builder()
				.name("userA")
				.email("asd@naver.com")
				.loginId("userA")
				.build();

		Recommendation re = Recommendation.builder()
							.check(1)
							.user(user)
							.post(post)
							.id(1L)
							.build();

		recommendationService.recommendation(post.getId());

	}


}
