package org.example.jwtExam;

import java.util.Arrays;

import org.example.jwtExam.jwt.util.JwtTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class JwtExamApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwtExamApplication.class, args);
	}

	@Autowired
	JwtTokenizer jwtTokenizer;

	//    @Bean
	public CommandLineRunner run() {
		return args -> {
			String accessToken = jwtTokenizer.createAccessToken(
				1L, "test@test.com", "test", "testuser", Arrays.asList("ROLE_USER")
			);

			log.info("AccessToken {}", accessToken);

			String refreshToken = jwtTokenizer.createRefreshToken(
				1L, "test@test.com", "test", "testuser", Arrays.asList("ROLE_USER")
			);

			log.info("RefreshTokem {}", refreshToken);

			//            Long id = jwtTokenizer.getUserIdFromToken(accessToken);
			//            log.info("id {}",id);

			Claims claims = jwtTokenizer.parseAccessToken(accessToken);
			log.info("Access Token claims {}", claims);
			log.info("userId :: {}", claims.get("userId"));
			//
			//            Claims refreshTokenClaims = jwtTokenizer.parseRefreshToken(refreshToken);
			//            log.info("Refresh Token claims {}",refreshTokenClaims);
		};

	}
}
