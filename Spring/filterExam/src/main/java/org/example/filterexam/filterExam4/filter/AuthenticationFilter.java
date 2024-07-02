package org.example.filterexam.filterExam4.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.example.filterexam.filterExam4.entity.User;
import org.example.filterexam.filterExam4.service.UserService;

public class AuthenticationFilter implements Filter {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			String path = request.getRequestURI(); // ex) /welcome, /loginform ...

			String auth = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("auth")) {
						auth = cookie.getValue();
						break;
					}
				}
			}

			if (auth != null) {
				// 요청 url에 따라 http://localhost:8080/admin => Role이 ROLE_ADMIN 인 사용자에게만 인가
				User user = userService.findByUsername(auth);
				if (user != null) {
					UserContext.setUser(user);
					if (path.equals("/admin")) {
						if (user.getRoles().stream()
							.anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
							filterChain.doFilter(servletRequest, servletResponse);
							UserContext.clear();
							return;
						} else {
							response.sendRedirect("/access-denied");
							UserContext.clear();
							return;
						}
					}

					if (!(path.equals("/loginform") || path.equals("/login"))) {
						filterChain.doFilter(servletRequest, servletResponse);
						UserContext.clear();
						return;
					}

					response.sendRedirect("/already-login");
					UserContext.clear();
					return;
				}
			}

			if (!(path.equals("/loginform") || path.equals("/login"))) {
				response.sendRedirect("/loginform");
				UserContext.clear();
				return;
			}

			filterChain.doFilter(servletRequest, servletResponse);
			UserContext.clear();

		} finally {
			UserContext.clear();
		}

	}
}
