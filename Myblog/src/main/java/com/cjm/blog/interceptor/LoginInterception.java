package com.cjm.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterception extends HandlerInterceptorAdapter {
    //使用预处理这个方法  请求未到达时 就拦截下来进行一个校验
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") ==null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
