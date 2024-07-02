package org.example.filterexam.filterExam3;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.filterexam.filterExam2.User;
import org.example.filterexam.filterExam2.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @GetMapping("/loginform")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "loginform";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user,
                        HttpServletResponse response) {
        if (user.getUsername().equals("lee") && user.getPassword().equals("123")) {
            Cookie cookie = new Cookie("auth", "lee");
            cookie.setPath("/");

            // 이렇게 생성된 쿠키는 클라이언트에게 보내져야 함
            response.addCookie(cookie);
            return "redirect:/welcome";
        }

        return "redirect:/loginform";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request) {
        // 로그인한 사용자에게만 보여줌
//        String auth = null;
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("auth")) {
//                    auth = cookie.getValue();
//                    return "info";
//                }
//            }
//        }
//
//        return "redirect:/loginform";

        User user = UserContext.getUser();
        if (user != null) {
            return "info";
        } else {
            return "redirect:/loginform";
        }
    }
}
