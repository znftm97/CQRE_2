package com.cqre.cqre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CqreApplicationTests {

	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Test
	void contextLoads() {
		String pw1 = passwordEncoder.encode("1234");
		String pw2 = passwordEncoder.encode("1234");

		System.out.println(pw1);
		System.out.println(pw2);
	}

}
