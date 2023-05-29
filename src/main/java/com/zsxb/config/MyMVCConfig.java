//package com.zsxb.config;
//
//import com.zsxb.interceptor.JWTInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.*;
//
////使用WebMVCConfigureAdapter可以来扩展SpringMVC的功能
////@EnableWebMvc //不要接管SpringMVC
//@Configuration
//public class MyMVCConfig extends WebMvcConfigurationSupport {
//
//    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        InterceptorRegistration registration = null;
//        registry.addInterceptor(handlerLoginHandlerInterceptorNotAutoWiredQuestion())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/",
//                        "/employee/login",
//                        "/user/login",
//                        "/user/logout");
//    }
//
//    /**
//     * 解决拦截器中不能依赖注入的问题
//     *
//     * @return
//     */
//    @Bean
//    public JWTInterceptor handlerLoginHandlerInterceptorNotAutoWiredQuestion() {
//        return new JWTInterceptor();
//    }
//}
