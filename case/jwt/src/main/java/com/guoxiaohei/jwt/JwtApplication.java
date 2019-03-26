package com.guoxiaohei.jwt;

import com.guoxiaohei.jwt.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Description: JwtApplication
 *
 * @author guoyupeng
 * @Date 2019/3/25 17:07
 */
@SpringBootApplication
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(
                new JwtFilter());
        registrationBean.addServletNames("jwtFilter");
        registrationBean.addUrlPatterns("/api/success");
        return registrationBean;
    }

}
