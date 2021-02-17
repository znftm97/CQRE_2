package com.cqre.cqre;

import com.cqre.cqre.entity.post.Comment;
import com.cqre.cqre.entity.post.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class CqreApplicationTests {

	@Test
	void contextLoads() {
		Post post = Post.builder()
				.content("aaaa")
				.title("123")
				.build();

		Comment comment1 = Comment.builder()
				.content("111")
				.id(1L)
				.post(post)
				.build();

		Comment comment2 = Comment.builder()
				.content("222")
				.id(2L)
				.post(post)
				.build();

		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		List<Long> collect = comments
				.stream()
				.map(c -> c.getId())
				.collect(Collectors.toList());

		System.out.println(collect);
	}

}
