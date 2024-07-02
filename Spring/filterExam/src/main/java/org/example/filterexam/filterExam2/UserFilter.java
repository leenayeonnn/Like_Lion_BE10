package org.example.filterexam.filterExam2;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 사용자가 요청하면서 보낸 값이 있다면
        // 추출해서 UserContext에다 저장하는 코드 구현

        try {
            User user = new User();
            user.setUsername("lee");
            user.setPassword("1234");

            UserContext.setUser(user);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {

            // 쓰레드풀을 이용하기에 기존 사용한 스레드를 또 쓸수 있음
            // 이때 ThreadLocal에 값이 있으면 안되기에 clear 필수!
            UserContext.clear();
        }
    }
}
