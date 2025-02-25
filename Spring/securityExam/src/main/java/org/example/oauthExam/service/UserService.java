package org.example.oauthExam.service;

import java.util.Collections;
import java.util.Optional;

import org.example.oauthExam.domain.Role;
import org.example.oauthExam.domain.User;
import org.example.oauthExam.repository.RoleRepository;
import org.example.oauthExam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	// private final PasswordEncoder passwordEncoder;

	//회원가입
	// @Transactional
	// public User registUser(User user) {
	// 	//role 추가
	// 	Role userRole = roleRepository.findByName("USER");
	// 	user.setRoles(Collections.singleton(userRole));
	// 	//password 암호화해서 넣어줘야한다.
	// 	user.setPassword(passwordEncoder.encode(user.getPassword()));
	//
	// 	return userRepository.save(user);
	// }

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> findByProviderAndSocialId(String provider, String socialId) {
		return userRepository.findByProviderAndSocialId(provider, socialId);
	}

	@Transactional(readOnly = false)
	public User saveUser(String username, String name, String email, String socialId, String provider, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setEmail(email);
		user.setSocialId(socialId);
		user.setProvider(provider);
		user.setPassword(passwordEncoder.encode(""));
		return userRepository.save(user);
	}
}
