package com.example.config;

import com.example.aop.annotation.After;
import com.example.aop.annotation.Aspect;
import com.example.aop.annotation.Before;
import com.example.aop.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author jameszhou
 */
@Aspect
@Component
public class LogAop {

    @Pointcut("execution(public * com.example.service.impl.*.*(..))")
    public void pointCut(){}

    @Before
    public void beforeLog() {
        System.out.println("业务执行前记录日志!");
    }

    @After
    public void afterLog() {
        System.out.println("业务执行后记录日志！");
    }
}
