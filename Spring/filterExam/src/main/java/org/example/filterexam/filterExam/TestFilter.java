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

@Slf4j
@WebFilter("/hi/*")
public class TestFilter implements Filter {
    public TestFilter(){
        log.info("=== test filter constructor () run ===");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("=== test filter init () run ===");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("=== test filter doFilter () run ===");

        filterChain.doFilter(servletRequest, servletResponse);

        log.info("=== test filter doFilter () end ===");
    }

    @Override
    public void destroy() {
        log.info("=== test filter destroy () run ===");
    }
}
