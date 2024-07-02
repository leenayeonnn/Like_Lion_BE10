package org.example.oauthExam.repository;

import java.util.Optional;

import org.example.oauthExam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

	Optional<User> findByProviderAndSocialId(String provider, String socialId);
}
