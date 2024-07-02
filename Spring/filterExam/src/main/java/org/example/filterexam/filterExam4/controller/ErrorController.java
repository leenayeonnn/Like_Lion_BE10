package org.example.filterexam.filterExam4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}

	@GetMapping("already-login")
	public String alreadyLogin() {
		return "already-login";
	}
}
