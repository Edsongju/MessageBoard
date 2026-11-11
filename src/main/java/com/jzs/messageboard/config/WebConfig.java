package com.jzs.messageboard.config;

import com.jzs.messageboard.interceptor.AdminInterceptor;
import com.jzs.messageboard.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截：只拦截用户个人中心相关
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/message/**")
                .excludePathPatterns("/", "/index", "/login", "/register",
                        "/doLogin", "/doRegister", "/css/**", "/js/**", "/images/**");

        // 管理员拦截：拦截所有后台页面
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/login", "/doLogin", "/css/**", "/js/**", "/images/**");
    }
}
