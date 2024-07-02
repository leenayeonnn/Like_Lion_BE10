package org.example.oauthExam.service;

import java.util.Optional;

import org.example.oauthExam.domain.SocialUser;
import org.example.oauthExam.repository.SocialUserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialUserService {
	private final SocialUserRepository socialUserRepository;

	public SocialUser saveOrUpdate(String socialId, String provider, String username, String email, String avatarUrl) {
		Optional<SocialUser> existingUSer = socialUserRepository.findBySocialIdAndProvider(socialId, provider);
		SocialUser socialUser;

		if (existingUSer.isPresent()) {
			socialUser = existingUSer.get();
			socialUser.setUsername(username);
			socialUser.setEmail(email);
			socialUser.setAvatarUrl(avatarUrl);
		} else {
			socialUser = new SocialUser();
			socialUser.setSocialId(socialId);
			socialUser.setProvider(provider);
			socialUser.setUsername(username);
			socialUser.setEmail(email);
			socialUser.setAvatarUrl(avatarUrl);
		}

		return socialUserRepository.save(socialUser);
	}
}
