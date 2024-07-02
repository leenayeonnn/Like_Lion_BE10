package org.example.filterexam.filterExam4.config;

import org.example.filterexam.filterExam4.filter.AuthenticationFilter;
import org.example.filterexam.filterExam4.service.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean // 필터 등록 시 @ 보다 config 방식을 이용하면 좀더 직관적, 디테일한 설정 가능
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(UserService userService) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserService(userService);
        registrationBean.setFilter(authenticationFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); //필터 실행시 우선 순위 설정 가능 / 숫자가 작을 수록 높음
        return registrationBean;
    }
}
