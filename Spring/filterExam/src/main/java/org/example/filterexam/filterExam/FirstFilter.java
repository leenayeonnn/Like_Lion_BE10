package org.example.filterexam.filterExam;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 필터는 필터 자체를 호출(요청) 할 수는 없음
// 필터는 어떤 요청을 처리하기 전, 후 에 해야할 일들을 처리 할 수 있다
@Slf4j
@WebFilter(urlPatterns = "/hi")
@Component
public class FirstFilter implements Filter {
    public FirstFilter() {
        log.info("=== first filter constructor () run ===");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=== first filter init () run ===");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("=== first filter doFilter () run ===");

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("=== first filter doFilter () end ===");

    }

    @Override
    public void destroy() {
        log.info("=== first filter destroy () run ===");
    }
}
