package org.example.filterexam.filterExam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    public HomeController(){
        log.info("=== HomeController () run ===");
    }

    @GetMapping("/")
    public String home() {
        log.info("=== home() run ===");
        return "home";
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
