package org.example.jwtExam.service;

import java.util.Collections;
import java.util.Optional;

import org.example.jwtExam.domain.Role;
import org.example.jwtExam.domain.User;
import org.example.jwtExam.repository.RoleRepository;
import org.example.jwtExam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	//회원가입
	@Transactional
	public User registUser(User user) {
		//role 추가
		Role userRole = roleRepository.findByName("USER");
		user.setRoles(Collections.singleton(userRole));
		//password 암호화해서 넣어줘야한다.
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}
}
