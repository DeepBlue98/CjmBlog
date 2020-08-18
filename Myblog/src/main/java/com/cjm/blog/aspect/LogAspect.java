package com.cjm.blog.aspect;


import org.aopalliance.intercept.Joinpoint;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.cjm.blog.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void dobefore(JoinPoint joinpoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName();
        Object[] args = joinpoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doafter(){
        logger.info("--------doafter------");
    }


    @AfterReturning(returning = "result",pointcut = "log()")
    public void doafterreturning(Object result){
        logger.info("Result : {}", result);
    }


    //搞个内部类 封装一下  方便取值
    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
