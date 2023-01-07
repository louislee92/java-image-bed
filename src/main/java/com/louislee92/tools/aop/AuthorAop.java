package com.louislee92.tools.aop;

import cn.hutool.core.util.StrUtil;
import com.louislee92.tools.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class AuthorAop {
    @Pointcut("execution(* com.louislee92.tools.controller..*.*(..))")
    public void point() {}

    /**
     * 前置通知
     * 连接点之前执行
     */
    @Before("point()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String url = request.getRequestURI();
        if("/login".equals(url)) return;    // 认证接口不拦截
        String code = request.getHeader("code");
        if(!SystemConfig.INST.IMG_CODE.equals(code))
            throw new RuntimeException("登录代码错误");

    }
}
