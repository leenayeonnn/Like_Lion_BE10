package org.example.filterexam.filterExam2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

//    public HomeController(UserService userService) {
//        this.userService = userService;
//        log.info("=== HomeController () run ===");
//    }

    @GetMapping("/")
    public String home() {
        log.info("=== home() run ===");
        return "home";
    }

    @GetMapping("/hi")
    public String hi() {
        log.info("=== hi() run === :: " + UserContext.getUser().toString());

        return "hi";
    }

    @GetMapping("/hello")
    public String hello() {
        log.info("=== hello() run ===");
        userService.list();
        return "hello";
    }
}
