package org.example.oauthExam.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "social_login_info")
@Getter
@Setter
public class SocialLoginInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String provider;
	private String socialId;
	private LocalDateTime createdAt;
	private String uuid;

	public SocialLoginInfo() {
		// 소셜 로그인한 시간, UUID
		// 소셜 로그인 후 특정 시간까지만 추가 작업 가능하게 하기 위함
		this.createdAt = LocalDateTime.now();
		this.uuid = UUID.randomUUID().toString();
	}
}
