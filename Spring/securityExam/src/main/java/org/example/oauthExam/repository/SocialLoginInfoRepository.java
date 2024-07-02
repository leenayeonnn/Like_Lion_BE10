package org.example.oauthExam.repository;

import java.util.Optional;

import org.example.oauthExam.domain.SocialLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLoginInfoRepository extends JpaRepository<SocialLoginInfo, Long> {
	Optional<SocialLoginInfo> findByProviderAndUuidAndSocialId(String provider, String uuid, String socialId);
}
