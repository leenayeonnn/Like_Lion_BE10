package org.example.filterexam.filterExam4.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.filterexam.filterExam4.entity.User;
import org.example.filterexam.filterExam4.filter.UserContext;
import org.example.filterexam.filterExam4.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인폼
    @GetMapping("/loginform")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "loginform";
    }

    //로그인
    @PostMapping("/login")
    public String login(@ModelAttribute(name = "user") User user,
                        Model model,
                        HttpServletResponse response) {

        User findUser = userService.findByUsername(user.getUsername());
        if (findUser != null && findUser.getPassword().equals(user.getPassword())) {
            Cookie cookie = new Cookie("auth", user.getUsername());
            cookie.setPath("/");
            cookie.setHttpOnly(true); // js로 쿠키 접근 X
            response.addCookie(cookie);

            // 같은 이름으로 쿠키가 다시 들어오면 쿠키 덮어씀
            // 로그아웃 구현에 적용 가능함
            return "redirect:/welcome";
        }

        model.addAttribute("user", new User());
        return "redirect:/loginform";
    }

    //welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    //info
    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // 쿠키 삭제
        // 브라우저의 쿠키는 서버에서 삭제 X
        // 같은 이름 쿠키를 덮어씌움
        Cookie cookie = new Cookie("auth", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return "redirect:/loginform";
    }
}
