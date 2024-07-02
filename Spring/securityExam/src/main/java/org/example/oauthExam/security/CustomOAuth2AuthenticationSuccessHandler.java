package org.example.oauthExam.security;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.oauthExam.domain.Role;
import org.example.oauthExam.domain.User;
import org.example.oauthExam.domain.SocialLoginInfo;
import org.example.oauthExam.service.SocialLoginInfoService;
import org.example.oauthExam.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final SocialLoginInfoService socialLoginInfoService;
	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		// redirect-uri: {baseUrl}/login/oauth2/code/{registrationId}
		String requestUri = request.getRequestURI();
		String provider = extractProviderFromUri(requestUri);
		// provider가 없는 경로 요청이 왔다는것은 문제가 발생한것이다.

		if (provider == null) {
			response.sendRedirect("/");
			return;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)auth.getPrincipal();

		int socialId = (int)defaultOAuth2User.getAttributes().get("id");
		String name = (String)defaultOAuth2User.getAttributes().get("name");

		Optional<User> userOptional = userService.findByProviderAndSocialId(provider, String.valueOf(socialId));
		if (userOptional.isPresent()) {
			// 회원 정보가 있으면 로그인 처리
			User user = userOptional.get();

			// CustomUserDetails 생성
			CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(),
				user.getName(), user.getRoles().stream().map(
				Role::getName).collect(Collectors.toList()));

			// Authentication 객체 생성 및 SecurityContext에 설정
			Authentication newAuth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
				customUserDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);

			response.sendRedirect("/welcome");

		} else { // 소셜로 아직 회원가입이 안되었을 때.
			// 소셜 로그인 정보 저장
			SocialLoginInfo socialLoginInfo = socialLoginInfoService.saveSocialLoginInfo(provider,
				String.valueOf(socialId));
			response.sendRedirect(
				"/registerSocialUser?provider=" + provider + "&socialId=" + socialId + "&name=" + name + "&uuid="
					+ socialLoginInfo.getUuid());
		}
	}

	private String extractProviderFromUri(String uri) {
		if (uri == null || uri.isBlank()) {
			return null;
		}

		if (!uri.startsWith("/login/oauth2/code/")) {
			return null;
		}

		// 예: /login/oauth2/code/github -> github
		String[] segments = uri.split("/");
		return segments[segments.length - 1];
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
	}
}
