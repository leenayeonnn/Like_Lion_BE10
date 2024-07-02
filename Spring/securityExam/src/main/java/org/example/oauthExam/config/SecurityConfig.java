package org.example.oauthExam.config;

import java.util.List;

import org.example.oauthExam.security.CustomOAuth2AuthenticationSuccessHandler;
import org.example.oauthExam.security.CustomUserDetailsService;
import org.example.oauthExam.service.SocialUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final SocialUserService socialUserService;
	private final CustomUserDetailsService customUserDetailsService;
	private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/userregform", "/userreg", "/", "loginform").permitAll()
				.requestMatchers("/oauth2/**", "/login/oauth2/code/github", "registerSocialUser", "/saveSocialUser").permitAll()
				.anyRequest().authenticated()
			)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(form -> form.disable())
			.cors(cors -> cors.configurationSource(configurationSource()))
			.httpBasic(httpBasic -> httpBasic.disable())
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/loginform")
				.failureUrl("/loginFailure")
				.userInfoEndpoint(userInfo -> userInfo
					.userService(this.oauth2UserService())
				)
				.successHandler(customOAuth2AuthenticationSuccessHandler)
			);

		return http.build();
	}

	public CorsConfigurationSource configurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowedMethods(List.of("GET", "POST", "DELETE"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
		return oauth2UserRequest -> {
			OAuth2User oauth2User = delegate.loadUser(oauth2UserRequest);
			// 여기에 Github 유저 정보를 처리하는 로직을 추가할 수 있습니다.
			// 예: DB에 사용자 정보 저장, 권한 부여 등

			String token = oauth2UserRequest.getAccessToken().getTokenValue();

			System.out.println(oauth2User.getAttributes().get("email"));

			// Save or update the user in the database
			String provider = oauth2UserRequest.getClientRegistration().getRegistrationId();
			String socialId = String.valueOf(oauth2User.getAttributes().get("id"));
			String username = (String)oauth2User.getAttributes().get("login");
			String email = (String)oauth2User.getAttributes().get("email");
			String avatarUrl = (String)oauth2User.getAttributes().get("avatar_url");

			socialUserService.saveOrUpdate(socialId, provider, username, email, avatarUrl);

			return oauth2User;
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
