package org.example.filterexam.filterExam2;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean // 필터 등록 시 @ 보다 config 방식을 이용하면 좀더 직관적, 디테일한 설정 가능
    public FilterRegistrationBean<UserFilter> authenticationFilter() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        UserFilter userFilter = new UserFilter();
        registrationBean.setFilter(userFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); //필터 실행시 우선 순위 설정 가능 / 숫자가 작을 수록 높음
        return registrationBean;
    }
}
