package org.example.securityexam3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequest -> authorizeRequest
				.requestMatchers("/shop/**", "/test").permitAll() // 이 페이지는 누구나 접근 가능
				.requestMatchers("/user/mypage").hasRole("USER")
				.requestMatchers("/admin/abc").hasRole("ADMIN")
				.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERUSER")
				.anyRequest() // 모든 요청
				.authenticated() // 인증 요구
			)
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user")
			.password(passwordEncoder().encode("1234"))
			.roles("USER")
			.build();

		UserDetails admin = User.withUsername("admin")
			.password(passwordEncoder().encode("1234"))
			.roles("ADMIN")
			.build();

		UserDetails superuser = User.withUsername("superuser")
			.password(passwordEncoder().encode("1234"))
			.roles("SUPERUSER")
			.build();

		UserDetails carami = User.withUsername("carami")
			.password(passwordEncoder().encode("1234"))
			.roles("ADMIN", "USER")
			.build();

		return new InMemoryUserDetailsManager(user, admin, superuser, carami);
	}
}
