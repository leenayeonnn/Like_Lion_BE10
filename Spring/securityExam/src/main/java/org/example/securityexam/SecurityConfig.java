package org.example.securityexam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequest -> authorizeRequest
				.anyRequest() // 모든 요청
				.authenticated() // 인증 요구
			);
		// .formLogin(Customizer.withDefaults()); // 기본 로그인 폼 사용

		http
			.formLogin(formLogin -> formLogin
				// .loginPage("/loginform")
				.defaultSuccessUrl("/success")
				.failureUrl("/fail")
				.successHandler((request, response, authentication) -> {
					log.info("authentication :: " + authentication.getName());
					response.sendRedirect("/success");
				})
				.failureHandler((request, response, exception) -> {
					log.info("exception :: " + exception.getMessage());
					response.sendRedirect("/fail");
				})
				// .usernameParameter("userId")
				// .passwordParameter("passwd")
				// .loginProcessingUrl("/login_p")
				.permitAll() // login page 에 대한 요청은 누구나 요청 가능
			);

		http
			.logout(logout -> logout
				// .logoutUrl("/log_out")
				.logoutSuccessUrl("/login")
				.addLogoutHandler((request, response, authentication) -> {
					log.info("logout :: " + authentication.getName());
					HttpSession session = request.getSession();
					session.invalidate();
				})
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("logout Success Handler :: " + authentication.getName());
					response.sendRedirect("/login");
				})
				.deleteCookies("remember-me")
			);

		return http.build();
	}
}
