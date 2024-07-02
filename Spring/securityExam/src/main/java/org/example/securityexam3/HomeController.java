package org.example.securityexam3;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/info")
	public String info() {
		return "info";
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/hi")
	public String hi() {
		return "hi";
	}

	@GetMapping("/loginform")
	public String loginform() {
		return "loginform";
	}

	@GetMapping("/success")
	public String success() {
		return "success";
	}

	@GetMapping("/fail")
	public String fail() {
		return "fail";
	}

	@GetMapping("/test")
	public String test() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		System.out.println(authentication.isAuthenticated());

		if (authentication == null || !authentication.isAuthenticated()
			|| authentication.getPrincipal() instanceof String) {
			return "익명 사용자";
		}

		UserDetails userDetails = (UserDetails)authentication.getPrincipal();

		return "username ::: " + userDetails.getUsername();
	}
}