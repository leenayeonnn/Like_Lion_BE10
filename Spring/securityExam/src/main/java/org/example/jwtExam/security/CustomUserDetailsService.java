package org.example.jwtExam.security;

import org.example.jwtExam.domain.User;
import org.example.jwtExam.repository.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("사용자가 없습니다.");
		}
		UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
		userBuilder.password(user.getPassword());
		userBuilder.roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new));

		return userBuilder.build();
	}
}
