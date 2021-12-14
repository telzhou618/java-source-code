package com.example.aspect;

import com.example.aop.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author telzhou
 */
@Aspect
@Component
public class LogAop {

    @Pointcut("execution(public * com.example.service.impl.*.*(..))")
    public void pointCut() {
    }

    @Before
    public void beforeLog() {
        System.out.println("业务方法执行前执行...");
    }

    @After
    public void afterLog() {
        System.out.println("业务方法执行后执行...");
    }

    @AfterThrowing
    public void afterThrowing() {
        System.out.println("业务方法执行异常时执行...");
    }
}
