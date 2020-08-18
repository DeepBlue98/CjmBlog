package com.cjm.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfigs implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterception())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

//    @Override
//    public void (InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterception())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin")
//                .excludePathPatterns("/admin/login");
//    }
}
