package com.cqre.cqre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CqreApplication {
	public static void main(String[] args) {
		SpringApplication.run(CqreApplication.class, args);
	}

}
