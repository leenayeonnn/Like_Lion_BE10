package org.example.filterexam.filterExam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @GetMapping("/info")
    public String info() {
        log.info("=== info() run ===");
        return "info";
    }

    @GetMapping("/list")
    public String list() {
        log.info("=== list() run ===");
        return "list";
    }
}
