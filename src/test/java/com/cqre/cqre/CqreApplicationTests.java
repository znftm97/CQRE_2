package com.cqre.cqre;

import com.cqre.cqre.controller.PostController;
import com.cqre.cqre.domain.User;
import com.cqre.cqre.domain.post.Board;
import com.cqre.cqre.domain.post.Post;
import com.cqre.cqre.domain.post.Recommendation;
import com.cqre.cqre.repository.post.PostRepository;
import com.cqre.cqre.service.PostService;
import com.cqre.cqre.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
							.user(user)
							.post(post)
							.id(1L)
							.build();

		recommendationService.recommendation(post.getId());

	}


}
