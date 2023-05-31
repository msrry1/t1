package com.zsxb.config;

import com.zsxb.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JWTInterceptor jwtInterceptor() {
        return new JWTInterceptor();
    }


    // 拦截器，只配置拦截哪些请求
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns(Arrays.asList(
                        "/studio/**",
                        "/employee/**",
                        "/schedule/**"
                ))
                .excludePathPatterns(Arrays.asList(
                        "/play/page/**",
                        "/**/login",
                        "/**/register",
                        "/**/login.html"
                ));
    }
}