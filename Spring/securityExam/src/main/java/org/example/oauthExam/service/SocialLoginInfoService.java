package org.example.oauthExam.service;

import java.util.Optional;

import org.example.oauthExam.domain.SocialLoginInfo;
import org.example.oauthExam.repository.SocialLoginInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialLoginInfoService {
	private final SocialLoginInfoRepository socialLoginInfoRepository;

	@Transactional(readOnly = false)
	public SocialLoginInfo saveSocialLoginInfo(String provider, String socialId) {
		SocialLoginInfo socialLoginInfo = new SocialLoginInfo();
		socialLoginInfo.setProvider(provider);
		socialLoginInfo.setSocialId(socialId);
		return socialLoginInfoRepository.save(socialLoginInfo);
	}

	public Optional<SocialLoginInfo> findByProviderAndUuidAndSocialId(String provider, String uuid, String socialId) {
		return socialLoginInfoRepository.findByProviderAndUuidAndSocialId(provider, uuid, socialId);
	}
}
